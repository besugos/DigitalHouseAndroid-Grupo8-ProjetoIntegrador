package com.besugos.marveluniverse.comic.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.CharacterSummaryModel

class ComicCharactersAdapter(private var listCharacters: MutableList<CharacterSummaryModel>) :
    RecyclerView.Adapter<ComicCharactersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicCharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view_details, parent, false)
        return ComicCharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicCharactersViewHolder, position: Int) {
        val event = listCharacters[position]
        holder.bind(event)
    }

    override fun getItemCount() = listCharacters.size

}