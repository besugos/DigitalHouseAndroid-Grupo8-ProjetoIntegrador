package com.besugos.marveluniverse.favorite.view

import android.graphics.BitmapFactory
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import de.hdodenhof.circleimageview.CircleImageView

class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var txtTitleFavoriteCard = view.findViewById<TextView>(R.id.txtTitleFavoriteCard)
    private val imgAvatarFavoriteCard = view.findViewById<CircleImageView>(R.id.imgAvatarFavoriteCard)

    fun bind(favorite: FavoriteModel) {
        txtTitleFavoriteCard.text = favorite.name
        imgAvatarFavoriteCard.setImageBitmap(BitmapFactory.decodeFile(favorite.pathImage))
    }
}