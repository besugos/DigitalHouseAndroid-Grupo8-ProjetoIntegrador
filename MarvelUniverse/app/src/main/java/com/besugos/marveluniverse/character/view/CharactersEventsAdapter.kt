package com.besugos.marveluniverse.character.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.EventSummaryModel

class CharactersEventsAdapter(private var listEvents: MutableList<EventSummaryModel>): RecyclerView.Adapter<CharactersEventsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersEventsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_recycler_view_details, parent, false)
        return CharactersEventsViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersEventsViewHolder, position: Int) {
        val event = listEvents[position]
        holder.bind(event)
    }

    override fun getItemCount() = listEvents.size

}
