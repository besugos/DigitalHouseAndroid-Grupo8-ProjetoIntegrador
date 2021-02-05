package com.besugos.marveluniverse.favorite.view

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.view.CharactersComicsAdapter
import com.besugos.marveluniverse.character.view.CharactersEventsAdapter
import com.besugos.marveluniverse.data.room.MyDataBase
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.repository.FavoriteRepository
import com.besugos.marveluniverse.favorite.viewmodel.FavoriteViewModel
import com.besugos.marveluniverse.home.model.ComicSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.besugos.marveluniverse.utils.GlobalVariables.Companion.isToUpdateFavorites
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var _eventsAdapter: CharactersEventsAdapter
    private lateinit var _comicsAdapter: CharactersComicsAdapter
    private lateinit var _favoriteViewModel: FavoriteViewModel
    private lateinit var _favorite: FavoriteModel

    private lateinit var txtTitleFavoriteDetail: TextView
    private lateinit var txtDescriptionFavoriteDetail: TextView
    private lateinit var imgAvatarFavoriteDetail: ImageView
    private lateinit var btnFavFavoriteDetail: ImageButton
    private lateinit var txtEventFavoriteDetail: TextView
    private lateinit var txtComicFavoriteDetail: TextView
    private lateinit var recyclerEventsFavoriteDetail: RecyclerView
    private lateinit var recyclerComicsFavoriteDetail: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)

        supportActionBar?.hide()

        _favorite = intent.getParcelableExtra("Favorite")!!

        initializeViews()
        initializeBasicsDataIntoViews()
        initializeEventsRecycler()
        initializeComicsRecycler()
        btnFavFavoriteDetailOnClick()

    }

    private fun initializeViews() {

        txtTitleFavoriteDetail = findViewById(R.id.txtTitleFavoriteDetail)
        txtDescriptionFavoriteDetail  = findViewById(R.id.txtDescriptionFavoriteDetail)
        imgAvatarFavoriteDetail = findViewById(R.id.imgAvatarFavoriteDetail)
        btnFavFavoriteDetail = findViewById(R.id.btnFavFavoriteDetail)
        txtEventFavoriteDetail = findViewById(R.id.txtEventFavoriteDetail)
        txtComicFavoriteDetail = findViewById(R.id.txtComicFavoriteDetail)
        recyclerEventsFavoriteDetail = findViewById(R.id.recyclerEventsFavoriteDetail)
        recyclerComicsFavoriteDetail = findViewById(R.id.recyclerComicsFavoriteDetail)

    }

    private fun initializeBasicsDataIntoViews() {
        txtTitleFavoriteDetail.text = _favorite.name
        txtDescriptionFavoriteDetail.text = if (_favorite.description.isNullOrEmpty()) {
            getText(R.string.character_description_not_found)
        } else _favorite.description
        imgAvatarFavoriteDetail.setImageBitmap(BitmapFactory.decodeFile(_favorite.pathImage))
    }

    private fun initializeEventsRecycler() {

        val gson = GsonBuilder().create()
        val type = object: TypeToken<MutableList<EventSummaryModel>>(){}.type
        val events = gson.fromJson<MutableList<EventSummaryModel>>(_favorite.events, type)

        if (!events.isNullOrEmpty()) {

            val eventsManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            _eventsAdapter = CharactersEventsAdapter(events)

            recyclerEventsFavoriteDetail.apply {
                setHasFixedSize(true)
                layoutManager = eventsManager
                adapter = _eventsAdapter
            }

        } else txtEventFavoriteDetail.visibility = View.GONE

    }

    private fun initializeComicsRecycler() {

        val gson = GsonBuilder().create()
        val type = object: TypeToken<MutableList<ComicSummaryModel>>(){}.type
        val comics = gson.fromJson<MutableList<ComicSummaryModel>>(_favorite.comics, type)

        if (!comics.isNullOrEmpty()){

            val comicsManager =
                LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

            _comicsAdapter = CharactersComicsAdapter(comics)

            recyclerComicsFavoriteDetail.apply {
                setHasFixedSize(true)
                layoutManager = comicsManager
                adapter = _comicsAdapter
            }

        } else txtComicFavoriteDetail.visibility = View.GONE

    }

    private fun btnFavFavoriteDetailOnClick() {

        btnFavFavoriteDetail.setOnClickListener{

            _favoriteViewModel = ViewModelProvider(
                this,
                FavoriteViewModel.FavoriteViewModelFactory(
                    FavoriteRepository(MyDataBase.getDataBaseClient(this).favoriteDAO())
                )
            ).get(FavoriteViewModel::class.java)

            _favoriteViewModel.removeFavorite(_favorite.id).observe(
                this, Observer { wasRemoved ->
                    if(wasRemoved) {
                        btnFavFavoriteDetail.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        isToUpdateFavorites = true
                        Toast.makeText(
                            this, "Favorite removed with success", Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
            )

        }

    }

}