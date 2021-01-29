package com.besugos.marveluniverse.comic.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.view.CharactersEventsAdapter
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.home.model.CharacterSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

const val IMG_RESOLUTION_SMALL = "standard_small"
const val IMG_RESOLUTION_FANTASTIC = "standard_fantastic"

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val _imgComicCard = view.findViewById<ImageView>(R.id.imgComicCard)
    private var _txtTitleComicCard = view.findViewById<TextView>(R.id.txtTitleComicCard)
    private val _txtDescriptionComicCard = view.findViewById<TextView>(R.id.txtDescriptionComicCard)


    fun bind(comic: ComicModel) {

        _txtTitleComicCard.text = comic.title

        Picasso.get()
            .load(comic.thumbnail?.getThumb(IMG_RESOLUTION_SMALL))
            .transform(CropCircleTransformation())
            .into(_imgComicCard)

        if (comic.description.isNullOrEmpty()) {
            _txtDescriptionComicCard.text =
                itemView.context.getString(R.string.comic_description_not_found)
        } else {
            _txtDescriptionComicCard.text = comic.description
        }

    }
}