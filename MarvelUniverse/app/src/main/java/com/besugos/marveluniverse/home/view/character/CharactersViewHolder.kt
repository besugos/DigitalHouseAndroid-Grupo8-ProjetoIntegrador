package com.besugos.marveluniverse.home.view.character

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.CharacterModel
import com.besugos.marveluniverse.home.view.event.EventsFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation


class CharactersViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameCharacterCard)
    private val description = view.findViewById<TextView>(R.id.txtCharecterCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarCharacterCard)

    lateinit var characterModel: CharacterModel

    fun bind(hero: CharacterModel) {
        characterModel = hero
        name.text = characterModel.name
        description.text = characterModel.description
        val imgUrl = context.getString(
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
            val inflater = context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.character_detail, null)
            val alertaDialog = BottomSheetDialog(context)


            layoutView.findViewById<TextView>(R.id.txtNameCharacterDetails).text =
                characterModel.name
            layoutView.findViewById<TextView>(R.id.txtDescriptionCharacterDetails).text =
                characterModel.description

            val event = layoutView.findViewById<TextView>(R.id.txtEventCharacterDetails)
            val story = layoutView.findViewById<TextView>(R.id.txtStoryCharacterDetails)
            val fav = layoutView.findViewById<ImageView>(R.id.imgFavoriteCharacterDetails)
//            Picasso.get().load(R.drawable.img1).into(layoutView.findViewById<ImageView>(R.id.imgAvatarCharacterDetails))

            if (characterModel.fav) {
                fav.setBackgroundResource(R.drawable.ic_action_favorite)
            } else {
                fav.setBackgroundResource(R.drawable.ic_action_favorite_border)
            }

            alertaDialog.apply {
                setContentView(layoutView)
                show()
            }

            event.setOnClickListener {
                alertaDialog.dismiss()
                val activity = view.context as AppCompatActivity
                val myFragment: Fragment = EventsFragment()
                activity.supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment, myFragment).addToBackStack(null).commit()

//                activity.supportFragmentManager.beginTransaction()
//                    .replace(R.id.fragment, CollectionFragment()).commit()

            }
            story.setOnClickListener {
                Toast.makeText(context, "Clicked", Toast.LENGTH_SHORT).show();
            }
//
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