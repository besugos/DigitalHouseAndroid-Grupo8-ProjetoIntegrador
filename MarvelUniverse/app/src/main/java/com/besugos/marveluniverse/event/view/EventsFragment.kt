package com.besugos.marveluniverse.event.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.event.model.EventModel


class EventsFragment : Fragment() {

    private lateinit var _adapter: EventAdapter
    private lateinit var _view: View
    private var _listEvent = mutableListOf<EventModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createEvents()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerEvent)
        val manager = LinearLayoutManager(view.context)

        _view = view

        _adapter = EventAdapter(
            requireContext(),
            _listEvent
        )

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }
    }


    fun createEvents(): MutableList<EventModel> {
        _listEvent.clear()
        for (i in 1..10) {
            val event = EventModel(
                i,
                "Event $i",
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null
            )
            _listEvent.add(event)
        }
        return _listEvent
    }


}