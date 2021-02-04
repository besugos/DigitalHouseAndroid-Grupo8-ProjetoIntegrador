package com.besugos.marveluniverse.event.view

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.event.model.EventModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameEventCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarEventCard)

    private lateinit var eventModel: EventModel

    fun bind(event: EventModel) {
        eventModel = event
        name.text = eventModel.title

        val imgUrl = eventModel.thumbnail!!.getThumb("standard_small")

        Picasso.get()
            .load(imgUrl)
            .transform(CropCircleTransformation())
            .into(avatar)

    }
}