package com.besugos.marveluniverse.event.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.event.model.EventModel
import com.besugos.marveluniverse.home.model.CharacterSummaryModel
import com.besugos.marveluniverse.home.model.ComicSummaryModel
import com.squareup.picasso.Picasso

class EventDetailsActivity : AppCompatActivity() {

    private lateinit var characterAdapter: EventCharactersAdapter
    private lateinit var comicsAdapter: EventComicAdapter
    private lateinit var event: EventModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        supportActionBar?.hide()

        event = intent.getParcelableExtra<EventModel>("Event")!!

        val eventName = this.findViewById<TextView>(R.id.txtNameEventDetails)

        eventName.text = event.title

        val eventDescription = this.findViewById<TextView>(R.id.txtDescriptionEventDetails)

        if (event.description.isNullOrEmpty()) {
            eventDescription.text = "Oops, no description for this hero :("
        } else {
            eventDescription.text = event.description
        }

        val imgUrl = event.thumbnail!!.getThumb("standard_fantastic")

        val eventImage = this.findViewById<ImageView>(R.id.imgAvatarEventDetails)

        Picasso.get()
            .load(imgUrl)
            .into(eventImage)

        val duration = this.findViewById<TextView>(R.id.txtEventYearDetails)

        if (event.start.isNullOrEmpty() || event.end.isNullOrEmpty()) {
            duration.text = this.getString(R.string.ops_not_available)
        } else {
            val startDate = event.start?.substring(0, 7)
            val endDate = event.end?.substring(0, 7)
            duration.text = "${startDate} - ${endDate}"
        }

        val recyclerViewCharacters =
            this.findViewById<RecyclerView>(R.id.eventDetailsCharacterList)
        val charactersManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listCharacters = mutableListOf<CharacterSummaryModel>()
        val characterDetails = event.characters?.items
        characterDetails?.forEach() {
            listCharacters.add(it)
        }

        if (listCharacters.isNullOrEmpty()) {
            val txtCharacters = this.findViewById<TextView>(R.id.txtCharacterEventDetails)
            txtCharacters.visibility = View.GONE
        }

        characterAdapter = EventCharactersAdapter(listCharacters)

        recyclerViewCharacters?.apply {
            setHasFixedSize(true)
            layoutManager = charactersManager
            adapter = characterAdapter
        }

        val recyclerViewComics =
            this.findViewById<RecyclerView>(R.id.eventDetailsComicList)
        val comicsManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listComics = mutableListOf<ComicSummaryModel>()
        val comicDetails = event.comics?.items
        comicDetails?.forEach() {
            listComics.add(it)
        }

        if (listComics.isNullOrEmpty()) {
            val txtComic = this.findViewById<TextView>(R.id.txtComicEventDetails)
            txtComic.visibility = View.GONE
        }


        comicsAdapter = EventComicAdapter(listComics)

        recyclerViewComics?.apply {
            setHasFixedSize(true)
            layoutManager = comicsManager
            adapter = comicsAdapter
        }


    }
}