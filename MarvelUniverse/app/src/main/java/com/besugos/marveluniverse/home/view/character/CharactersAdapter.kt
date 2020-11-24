package com.besugos.marveluniverse.home.view.character

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.CharacterModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CharactersAdapter(
    private val context: Context,
    private var heros: MutableList<CharacterModel>
) :
    RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_character_item, parent, false)
        return CharactersViewHolder(context, view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val hero = heros[position]
        holder.bind(hero)

    }

    override fun getItemCount() = heros.size
}