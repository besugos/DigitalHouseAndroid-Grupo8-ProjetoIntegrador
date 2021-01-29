package com.besugos.marveluniverse.character.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.ComicSummaryModel

class CharactersComicsAdapter(private var listComics: MutableList<ComicSummaryModel>) :
    RecyclerView.Adapter<CharactersComicsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersComicsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view_details, parent, false)
        return CharactersComicsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersComicsViewHolder, position: Int) {
        val comic = listComics[position]
        holder.bind(comic)
    }

    override fun getItemCount() = listComics.size

}