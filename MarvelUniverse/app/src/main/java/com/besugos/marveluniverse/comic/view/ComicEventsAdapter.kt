package com.besugos.marveluniverse.comic.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.EventSummaryModel

class ComicEventsAdapter(private var listEvents: MutableList<EventSummaryModel>) :
    RecyclerView.Adapter<ComicEventsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicEventsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_recycler_view_details, parent, false)
        return ComicEventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComicEventsViewHolder, position: Int) {
        val event = listEvents[position]
        holder.bind(event)
    }

    override fun getItemCount() = listEvents.size

}