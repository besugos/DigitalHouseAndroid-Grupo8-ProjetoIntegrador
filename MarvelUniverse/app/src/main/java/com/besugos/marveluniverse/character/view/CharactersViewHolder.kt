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
        description.text = characterModel.description
        val imgUrl = itemView.context.getString(
            R.string.characters_image,
            characterModel.thumbnail?.path,
            characterModel.thumbnail?.extension
        )
        Picasso.get()
            .load(R.drawable.img1)
            .transform(CropCircleTransformation())
            .into(avatar)
    }

    init {
        view.setOnClickListener {
            val inflater = itemView.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.character_detail, null)
            val alertaDialog = BottomSheetDialog(itemView.context)


            layoutView.findViewById<TextView>(R.id.txtNameCharacterDetails).text =
                characterModel.name
            layoutView.findViewById<TextView>(R.id.txtDescriptionCharacterDetails).text =
                characterModel.description

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