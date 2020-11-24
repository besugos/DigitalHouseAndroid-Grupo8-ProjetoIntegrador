package com.besugos.marveluniverse.home.view.favorites

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.FavoriteModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class FavoriteViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameFavoriteCard)
    private val favTxt = view.findViewById<TextView>(R.id.txtFavoriteCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarFavoriteCard)

    lateinit var favoriteModel: FavoriteModel

    fun bind(favorite: FavoriteModel) {
        favoriteModel = favorite
        name.text = favoriteModel.name
        favTxt.text = context.getString(R.string.favorites_short_description, favoriteModel.id)
        Picasso.get()
            .load(R.drawable.img4)
            .transform(CropCircleTransformation())
            .into(avatar)
    }
}