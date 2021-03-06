package com.besugos.marveluniverse.character.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.ComicSummaryModel

class CharactersComicsViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.itemRecyclerDetails)

    fun bind(comic: ComicSummaryModel) {

        name.text = comic.name

    }
}