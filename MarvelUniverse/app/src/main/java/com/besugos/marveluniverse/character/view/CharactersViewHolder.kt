package com.besugos.marveluniverse.character.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.model.CharacterModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class CharactersViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameCharacterCard)
    private val description = view.findViewById<TextView>(R.id.txtCharecterCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarCharacterCard)

    lateinit var characterModel: CharacterModel

    fun bind(hero: CharacterModel) {
        characterModel = hero
        name.text = characterModel.name
        if (characterModel.description.isNullOrEmpty()){
            description.text = "Oops, no description for this hero :("
        } else {
            description.text = characterModel.description
        }
        val imgUrl = characterModel.thumbnail!!.getThumb("standard_small")

        Picasso.get()
            .load(imgUrl)
            .transform(CropCircleTransformation())
            .into(avatar)
    }

    init {
        view.setOnClickListener {
            val inflater = itemView.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.character_detail, null)
            val alertaDialog = BottomSheetDialog(itemView.context)

            val characterName = layoutView.findViewById<TextView>(R.id.txtNameCharacterDetails)

            characterName.text = characterModel.name

            val characterDescription = layoutView.findViewById<TextView>(R.id.txtDescriptionCharacterDetails)

            if (characterModel.description.isNullOrEmpty()){
                characterDescription.text  = "Oops, no description for this hero :("
            } else {
                characterDescription.text = characterModel.description
            }

            val imgUrl = characterModel.thumbnail!!.getThumb("standard_large")

            val image = layoutView.findViewById<ImageView>(R.id.imgAvatarCharacterDetails)

            Picasso.get()
                .load(imgUrl)
                .into(image)

            val fav = layoutView.findViewById<ImageView>(R.id.imgFavoriteCharacterDetails)

            if (characterModel.fav) {
                fav.setBackgroundResource(R.drawable.ic_action_favorite)
            } else {
                fav.setBackgroundResource(R.drawable.ic_action_favorite_border)
            }

            alertaDialog.apply {
                setContentView(layoutView)
                show()
            }

            fav.setOnClickListener {
                if (characterModel.fav) {
                    fav.setBackgroundResource(R.drawable.ic_action_favorite_border)
                    characterModel.fav = !characterModel.fav
                } else {
                    characterModel.fav = !characterModel.fav
                    fav.setBackgroundResource(R.drawable.ic_action_favorite)
                }
            }
        }
    }
}