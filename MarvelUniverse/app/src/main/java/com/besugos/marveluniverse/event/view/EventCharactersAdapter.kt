package com.besugos.marveluniverse.event.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.view.CharactersComicsViewHolder
import com.besugos.marveluniverse.home.model.CharacterSummaryModel

class EventCharactersAdapter(private var listCharacters: MutableList<CharacterSummaryModel>) :
    RecyclerView.Adapter<EventCharactersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventCharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view_details, parent, false)
        return EventCharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventCharactersViewHolder, position: Int) {
        val character = listCharacters[position]
        holder.bind(character)
    }

    override fun getItemCount() = listCharacters.size

}