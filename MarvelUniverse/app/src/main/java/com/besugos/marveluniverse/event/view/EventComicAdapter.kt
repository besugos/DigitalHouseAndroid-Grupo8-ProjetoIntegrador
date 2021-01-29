package com.besugos.marveluniverse.event.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.view.CharactersComicsViewHolder
import com.besugos.marveluniverse.home.model.ComicSummaryModel

class EventComicAdapter(private var listComics: MutableList<ComicSummaryModel>) :
    RecyclerView.Adapter<EventComicViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventComicViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view_details, parent, false)
        return EventComicViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventComicViewHolder, position: Int) {
        val comic = listComics[position]
        holder.bind(comic)
    }

    override fun getItemCount() = listComics.size

}