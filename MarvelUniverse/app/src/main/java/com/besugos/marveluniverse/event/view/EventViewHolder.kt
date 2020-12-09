package com.besugos.marveluniverse.event.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.event.model.EventModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    private var name = view.findViewById<TextView>(R.id.txtNameEventCard)
    private val eventTxt = view.findViewById<TextView>(R.id.txtEventCard)
    private val avatar = view.findViewById<ImageView>(R.id.imgAvatarEventCard)

    lateinit var eventModel: EventModel

    fun bind(event: EventModel) {
        eventModel = event
        name.text = eventModel.title

        if (eventModel.description.isNullOrEmpty()){
            eventTxt.text  = "Oops, no description for this hero :("
        } else {
            eventTxt.text = eventModel.description
        }

        val imgUrl = eventModel.thumbnail!!.getThumb("standard_large")

        Picasso.get()
            .load(imgUrl)
            .into(avatar)

    }

    init {
        view.setOnClickListener {
            val inflater = itemView.context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val layoutView = inflater.inflate(R.layout.events_detail, null)
            val alertaDialog = BottomSheetDialog(itemView.context)

            val eventName = layoutView.findViewById<TextView>(R.id.txtNameEventrDetails)

            eventName.text = eventModel.title

            val eventDescription = layoutView.findViewById<TextView>(R.id.txtDescriptionEventDetails)

            if (eventModel.description.isNullOrEmpty()){
                eventDescription.text  = "Oops, no description for this hero :("
            } else {
                eventDescription.text = eventModel.description
            }

            val imgUrl = eventModel.thumbnail!!.getThumb("standard_large")

            val eventImage = layoutView.findViewById<ImageView>(R.id.imgAvatarEventDetails)

            Picasso.get()
                .load(imgUrl)
                .into(eventImage)

            alertaDialog.apply {
                setContentView(layoutView)
                show()
            }

        }
    }
}
