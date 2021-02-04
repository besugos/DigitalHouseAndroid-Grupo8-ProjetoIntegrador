package com.besugos.marveluniverse.favorite.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.squareup.picasso.Picasso

class FavoriteDetailActivity : AppCompatActivity() {

    private lateinit var favorite: FavoriteModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_detail)

        favorite = intent.getParcelableExtra<FavoriteModel>("Favorite")!!

        supportActionBar?.hide()

        val characterName = this.findViewById<TextView>(R.id.txtNameFavoritesDetails)
        characterName.text = favorite.name

        val characterDescription = this.findViewById<TextView>(R.id.txtDescriptionFavoriteDetails)
        characterDescription.text =
            if (favorite.description.isNullOrEmpty()) {
                this.getText(R.string.character_description_not_found)
            } else favorite.description

        val imgHero = this.findViewById<ImageView>(R.id.imgAvatarFavoritesDetails)
        Picasso.get()
            .load(ImageModel(favorite.path!!, favorite.extension!!).getThumb("standard_fantastic"))
            .into(imgHero)
    }
}