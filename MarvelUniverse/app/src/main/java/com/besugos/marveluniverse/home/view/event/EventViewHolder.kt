package com.besugos.marveluniverse.home.view.event

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.EventModel
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class EventViewHolder(val context: Context, view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameEventCard)
    private val eventTxt = view.findViewById<TextView>(R.id.txtEventCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarEventCard)

    lateinit var eventModel: EventModel

    fun bind(event: EventModel) {
        eventModel = event
        name.text = eventModel.title
        eventTxt.text = context.getString(R.string.events_short_description, eventModel.id)
        Picasso.get()
            .load(R.drawable.img1)
            .transform(CropCircleTransformation())
            .into(avatar)
    }
}