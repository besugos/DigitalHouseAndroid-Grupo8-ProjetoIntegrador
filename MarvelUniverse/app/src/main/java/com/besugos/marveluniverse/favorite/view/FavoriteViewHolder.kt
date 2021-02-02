package com.besugos.marveluniverse.favorite.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameFavoriteCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarFavoriteCard)

    fun bind(favorite: FavoriteModel) {
        name.text = favorite.name

        val imgUrl = ImageModel(favorite.path!!, favorite.extension!!).getThumb("standard_small")

        Picasso.get()
            .load(imgUrl)
            .transform(CropCircleTransformation())
            .into(avatar)
    }
}