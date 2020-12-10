package com.besugos.marveluniverse.comic.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.comic.model.ComicModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class ComicViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameStoryCard)
    private val eventTxt = view.findViewById<TextView>(R.id.txtStoryCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarStoryCard)

    private lateinit var comicModel: ComicModel

    fun bind(comic: ComicModel) {
        comicModel = comic
        name.text = comicModel.title
        eventTxt.text = comic.description
        Picasso.get()
            .load(comic.thumbnail?.getThumb())
            .transform(CropCircleTransformation())
            .into(avatar)
    }


    init {
        view.setOnClickListener {
            val inflater = itemView.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.stories_detail, null)
            val alertaDialog = BottomSheetDialog(itemView.context)

            layoutView.findViewById<TextView>(R.id.txtNameStoriesDetails).text =
                comicModel.title

            alertaDialog.apply {
                setContentView(layoutView)
                show()
            }
        }
    }

}