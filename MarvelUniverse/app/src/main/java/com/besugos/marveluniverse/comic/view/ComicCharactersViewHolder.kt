package com.besugos.marveluniverse.comic.view

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.CharacterSummaryModel

class ComicCharactersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val name = view.findViewById<TextView>(R.id.itemRecyclerDetails)

    fun bind(event: CharacterSummaryModel) {

        name.text = event.name

    }
}