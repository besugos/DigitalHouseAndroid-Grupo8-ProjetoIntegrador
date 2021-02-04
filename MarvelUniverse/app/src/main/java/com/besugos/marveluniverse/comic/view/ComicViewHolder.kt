package com.besugos.marveluniverse.comic.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.comic.model.ComicModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

const val IMG_RESOLUTION_SMALL = "standard_small"
const val IMG_RESOLUTION_FANTASTIC = "standard_fantastic"

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val _imgComicCard = view.findViewById<ImageView>(R.id.imgComicCard)
    private var _txtTitleComicCard = view.findViewById<TextView>(R.id.txtTitleComicCard)


    fun bind(comic: ComicModel) {

        _txtTitleComicCard.text = comic.title

        Picasso.get()
            .load(comic.thumbnail?.getThumb(IMG_RESOLUTION_SMALL))
            .transform(CropCircleTransformation())
            .into(_imgComicCard)

    }
}