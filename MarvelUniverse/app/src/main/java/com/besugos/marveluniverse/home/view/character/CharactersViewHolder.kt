package com.besugos.marveluniverse.home.view.character

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.CharacterModel
import com.besugos.marveluniverse.utlis.Constants.Companion.CHARACTER_BUNDLE
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class CharactersViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameCharacterCard)
    private val event = view.findViewById<TextView>(R.id.txtCharecterCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarCharacterCard)

    lateinit var characterModel: CharacterModel

    fun bind(hero: CharacterModel) {
        characterModel = hero
        name.text = characterModel.name
        event.text = context.getString(R.string.characters_short_description, characterModel.id)
        Picasso.get()
            .load(R.drawable.img2)
            .transform(CropCircleTransformation())
            .into(avatar)
    }

    init {
        view.setOnClickListener {
            val alertaDialog = MaterialAlertDialogBuilder(context, R.style.Details)

            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.character_detail, null)

//            Picasso.get().load(R.drawable.img1).into(layoutView.findViewById<ImageView>(R.id.imgAvatarCharacterDetails))

            alertaDialog.apply {
                setView(layoutView)
                show()
            }



        }

    }


}