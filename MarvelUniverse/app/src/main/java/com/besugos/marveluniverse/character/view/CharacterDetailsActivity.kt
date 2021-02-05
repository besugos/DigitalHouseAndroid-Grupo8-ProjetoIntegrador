package com.besugos.marveluniverse.character.view

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.drawToBitmap
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.model.CharacterModel
import com.besugos.marveluniverse.data.room.MyDataBase
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.repository.FavoriteRepository
import com.besugos.marveluniverse.favorite.viewmodel.FavoriteViewModel
import com.besugos.marveluniverse.home.model.ComicSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.besugos.marveluniverse.utils.GlobalVariables.Companion.isToUpdateFavorites
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var _eventsAdapter: CharactersEventsAdapter
    private lateinit var _comicsAdapter: CharactersComicsAdapter
    private lateinit var _favoriteViewModel: FavoriteViewModel
    private lateinit var _character: CharacterModel

    private lateinit var txtTitleCharacterDetail: TextView
    private lateinit var txtDescriptionCharacterDetail: TextView
    private lateinit var imgAvatarCharacterDetail: ImageView
    private lateinit var btnFavCharacterDetail: ImageButton
    private lateinit var btnShareCharacterDetail: ImageButton
    private lateinit var txtEventCharacterDetail: TextView
    private lateinit var txtComicCharacterDetail: TextView
    private lateinit var recyclerEventsCharacterDetail: RecyclerView
    private lateinit var recyclerComicsCharacterDetail: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        supportActionBar?.hide()

        auth = FirebaseAuth.getInstance()
        _character = intent.getParcelableExtra("Character")!!

        initializeViews()
        initializeBasicsDataIntoViews()
        initializeFavoriteViewModel()
        initializeEventsRecycler()
        initializeComicsRecycler()
        btnFavCharacterDetailOnClick()
        btnShareCharacterDetailOnClick()

    }

    private fun initializeViews() {

        txtTitleCharacterDetail = findViewById(R.id.txtTitleCharacterDetail)
        txtDescriptionCharacterDetail  = findViewById(R.id.txtDescriptionCharacterDetail)
        imgAvatarCharacterDetail = findViewById(R.id.imgAvatarCharacterDetail)
        btnFavCharacterDetail = findViewById(R.id.btnFavCharacterDetail)
        btnShareCharacterDetail = findViewById(R.id.btnShareCharacterDetail)
        txtEventCharacterDetail = findViewById(R.id.txtEventCharacterDetail)
        txtComicCharacterDetail = findViewById(R.id.txtComicCharacterDetail)
        recyclerEventsCharacterDetail = findViewById(R.id.recyclerEventsCharacterDetail)
        recyclerComicsCharacterDetail = findViewById(R.id.recyclerComicsCharacterDetail)

    }

    private fun initializeBasicsDataIntoViews() {
        txtTitleCharacterDetail.text = _character.name
        txtDescriptionCharacterDetail.text = if (_character.description.isNullOrEmpty()) {
            getText(R.string.character_description_not_found)
        } else _character.description
        Picasso.get()
            .load(_character.thumbnail!!.getThumb("standard_fantastic"))
            .into(imgAvatarCharacterDetail)
    }

    private fun initializeFavoriteViewModel() {

        _favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                FavoriteRepository(MyDataBase.getDataBaseClient(this).favoriteDAO())
            )
        ).get(FavoriteViewModel::class.java)
        _favoriteViewModel.getFavoriteById(_character.id!!).observe(this, Observer {
            _character.fav = it != null

            btnFavCharacterDetail.setBackgroundResource(
                if (_character.fav) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )

        })

    }

    private fun initializeEventsRecycler() {

        val eventsManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val events = mutableListOf<EventSummaryModel>()
        val eventsDetails = _character.events?.items
        eventsDetails?.forEach { events.add(it) }

        if (events.isNullOrEmpty()) txtEventCharacterDetail.visibility = View.GONE

        _eventsAdapter = CharactersEventsAdapter(events)

        recyclerEventsCharacterDetail.apply {
            setHasFixedSize(true)
            layoutManager = eventsManager
            adapter = _eventsAdapter
        }

    }

    private fun initializeComicsRecycler() {

        val comicsManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val comics = mutableListOf<ComicSummaryModel>()
        val comicsDetails = _character.comics?.items
        comics.clear()
        comicsDetails?.forEach { comics.add(it) }

        if (comics.isNullOrEmpty()) txtComicCharacterDetail.visibility = View.GONE

        _comicsAdapter = CharactersComicsAdapter(comics)

        recyclerComicsCharacterDetail.apply {
            setHasFixedSize(true)
            layoutManager = comicsManager
            adapter = _comicsAdapter
        }

    }

    private fun btnFavCharacterDetailOnClick() {

        btnFavCharacterDetail.setOnClickListener {

            _character.fav = !_character.fav
            if (_character.fav) {

                _favoriteViewModel.insertFavorite(getFavoriteModelToSave()).observe(
                    this, Observer { wasUnlocked ->
                        if (wasUnlocked) {
                            btnFavCharacterDetail.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                            isToUpdateFavorites = true
                        }
                    }
                )

            } else {

                _favoriteViewModel.removeFavorite(_character.id!!).observe(
                    this, Observer { wasUnlocked ->
                        if (wasUnlocked) {
                            btnFavCharacterDetail.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                            isToUpdateFavorites = true
                        }
                    }
                )

            }
        }

    }

    private fun btnShareCharacterDetailOnClick() {

        btnShareCharacterDetail.setOnClickListener{
            val wikiUrl = _character.urls?.first { it.type == "wiki" }
            val url = wikiUrl?.url
                ?: ((_character.urls?.first { it.type == "detail" } ?: "https://www.marvel.com") as String)

            val intent = Intent()
            intent.action = Intent.ACTION_SEND
            intent.type = "text/plain"
            intent.putExtra(Intent.EXTRA_SUBJECT, "Sharing URL")
            intent.putExtra( Intent.EXTRA_TEXT, url)
            startActivity(Intent.createChooser(intent, "Share URL"))

        }

    }

    private fun getFavoriteModelToSave(): FavoriteModel {

        var path = ""
        val bitmap = imgAvatarCharacterDetail.drawToBitmap()

        try {
            var file = this.getDir("Images", Context.MODE_PRIVATE)
            file = File(file, "img_${_character.id!!}.jpg")
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 85, out)
            out.flush()
            out.close()
            path = file.path
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val events = if(!_character.events?.items.isNullOrEmpty()) {
            Gson().toJson(_character.events!!.items)
        } else ""

        val comics = if(!_character.comics?.items.isNullOrEmpty()) {
            Gson().toJson(_character.comics!!.items)
        } else ""

        return FavoriteModel(
            _character.id!!,
            auth.currentUser!!.uid,
            _character.name,
            _character.description,
            path,
            events,
            comics
        )

    }

}