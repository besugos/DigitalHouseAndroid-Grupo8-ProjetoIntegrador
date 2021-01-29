package com.besugos.marveluniverse.character.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.model.CharacterModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class CharactersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameCharacterCard)
    private val description = view.findViewById<TextView>(R.id.txtCharecterCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarCharacterCard)

    fun bind(hero: CharacterModel) {
        name.text = hero.name
        if (hero.description.isNullOrEmpty()) {
            description.text = itemView.context.getText(R.string.character_description_not_found)
        } else {
            description.text = hero.description
        }
        val imgUrl = hero.thumbnail!!.getThumb("standard_small")

        Picasso.get()
            .load(imgUrl)
            .transform(CropCircleTransformation())
            .into(avatar)
    }

}