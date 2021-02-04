package com.besugos.marveluniverse.comic.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.comic.viewmodel.ComicViewModel
import com.besugos.marveluniverse.home.model.CharacterSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.squareup.picasso.Picasso

class ComicDetails : AppCompatActivity() {

    private lateinit var eventsAdapter: ComicEventsAdapter
    private lateinit var charactersAdapter: ComicCharactersAdapter
    private lateinit var _comic: ComicModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_details)

        supportActionBar?.hide()

        _comic= intent.getParcelableExtra<ComicModel>("Comic")!!

        val txtTitleComicDetail = this.findViewById<TextView>(R.id.txtTitleComicDetail)
        val imgComicDetail = this.findViewById<ImageView>(R.id.imgComicDetail)
        val txtDescriptionComicDetail =
            this.findViewById<TextView>(R.id.txtDescriptionComicDetail)

        txtTitleComicDetail.text = _comic.title

        Picasso.get()
            .load(_comic.thumbnail?.getThumb(IMG_RESOLUTION_FANTASTIC))
            .into(imgComicDetail)

        val recyclerViewEvents = this.findViewById<RecyclerView>(R.id.comicDetailsEventList)
        val eventsManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listEvents = mutableListOf<EventSummaryModel>()
        val eventsDetails = _comic.events?.items
        eventsDetails?.forEach() {
            listEvents.add(it)
        }

        if (listEvents.isNullOrEmpty()) {
            val txtEvent = this.findViewById<TextView>(R.id.txtEventComicDetails)
            txtEvent.visibility = View.GONE
        }

        eventsAdapter = ComicEventsAdapter(listEvents)

        recyclerViewEvents?.apply {
            setHasFixedSize(true)
            layoutManager = eventsManager
            adapter = eventsAdapter
        }

        val recyclerViewCharacters =
            this.findViewById<RecyclerView>(R.id.comicDetailsCharacterList)
        val charactersManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        val listCharacters = mutableListOf<CharacterSummaryModel>()
        val charactersDetails = _comic.characters?.items
        charactersDetails?.forEach() {
            listCharacters.add(it)
        }

        if (listCharacters.isNullOrEmpty()) {
            val txtCharacters = this.findViewById<TextView>(R.id.txtCharacterComicDetails)
            txtCharacters.visibility = View.GONE
        }

        charactersAdapter = ComicCharactersAdapter(listCharacters)

        recyclerViewCharacters?.apply {
            setHasFixedSize(true)
            layoutManager = charactersManager
            adapter = charactersAdapter
        }

        if (_comic.description.isNullOrEmpty()) {
            txtDescriptionComicDetail.text =
                this.getString(R.string.comic_description_not_found)
        } else {
            txtDescriptionComicDetail.text = _comic.description
        }

    }
}
