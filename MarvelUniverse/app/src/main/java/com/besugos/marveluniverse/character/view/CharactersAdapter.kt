package com.besugos.marveluniverse.character.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.model.CharacterModel

class CharactersAdapter(
    private var dataSet: MutableList<CharacterModel>,
    private val listener: (CharacterModel) -> Unit
) :
    RecyclerView.Adapter<CharactersViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.card_character_item, parent, false)
        return CharactersViewHolder(view)
    }

    override fun getItemCount() = dataSet.size

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val hero = dataSet[position]
        holder.bind(hero)
        holder.itemView.setOnClickListener { listener(hero) }
    }

}