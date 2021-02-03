package com.besugos.marveluniverse.character.view

import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
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
import com.squareup.picasso.Picasso

class CharacterDetailsActivity : AppCompatActivity() {

    private lateinit var eventsAdapter: CharactersEventsAdapter
    private lateinit var comicsAdapter: CharactersComicsAdapter
    private lateinit var character: CharacterModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_details)

        supportActionBar?.hide()

        character = intent.getParcelableExtra<CharacterModel>("Character")!!

        val characterName = this.findViewById<TextView>(R.id.txtNameCharacterDetails)
        characterName.text = character.name

        val characterDescription =
            this.findViewById<TextView>(R.id.txtDescriptionCharacterDetails)
        characterDescription.text =
            if (character.description.isNullOrEmpty()) {
                this.getText(R.string.character_description_not_found)
            } else character.description

        val imgHero = this.findViewById<ImageView>(R.id.imgAvatarCharacterDetails)
        Picasso.get()
            .load(character.thumbnail!!.getThumb("standard_fantastic"))
            .into(imgHero)

        val recyclerViewEvents =
            this.findViewById<RecyclerView>(R.id.characterDetailsEventsList)
        val eventsManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listEvents = mutableListOf<EventSummaryModel>()
        val eventsDetails = character.events?.items
        eventsDetails?.forEach() {
            listEvents.add(it)
        }

        if (listEvents.isNullOrEmpty()) {
            val txtEvent = this.findViewById<TextView>(R.id.txtEventCharacterDetails)
            txtEvent.visibility = View.GONE
        }

        eventsAdapter = CharactersEventsAdapter(listEvents)

        recyclerViewEvents?.apply {
            setHasFixedSize(true)
            layoutManager = eventsManager
            adapter = eventsAdapter
        }

        val recyclerViewComics =
            this.findViewById<RecyclerView>(R.id.characterDetailsComicsList)
        val comicsManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listComics = mutableListOf<ComicSummaryModel>()
        val comicsDetails = character.comics?.items
        listComics.clear()
        comicsDetails?.forEach() {
            listComics.add(it)
        }

        if (listComics.isNullOrEmpty()) {
            val txtComic = this.findViewById<TextView>(R.id.txtComicCharacterDetails)
            txtComic.visibility = View.GONE
        }

        comicsAdapter = CharactersComicsAdapter(listComics)

        recyclerViewComics?.apply {
            setHasFixedSize(true)
            layoutManager = comicsManager
            adapter = eventsAdapter
        }


        val btnToggleFavorite = this.findViewById<ImageButton>(R.id.btnToggleFavorite)

        val favoriteModel = FavoriteModel(
            character.id!!,
            character.name,
            character.description,
            character.thumbnail!!.path,
            character.thumbnail!!.extension
        )

        val favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                FavoriteRepository(MyDataBase.getDataBaseClient(this).favoriteDAO())
            )
        ).get(FavoriteViewModel::class.java)
        favoriteViewModel.getFavoriteById(favoriteModel.id).observe(this, Observer {
            character.fav = it != null

            btnToggleFavorite.setBackgroundResource(
                if (character.fav) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )

        })

        btnToggleFavorite.setOnClickListener {
            character.fav = !character.fav
            if (character.fav) {
                favoriteViewModel.insertFavorite(favoriteModel).observe(
                    this, Observer { wasUnlocked ->
                        if (wasUnlocked) {
                            btnToggleFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        }
                    }
                )
            } else {
                favoriteViewModel.removeFavorite(favoriteModel).observe(
                    this, Observer { wasUnlocked ->
                        if (wasUnlocked) {
                            btnToggleFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        }
                    }
                )
            }
        }


    }
}