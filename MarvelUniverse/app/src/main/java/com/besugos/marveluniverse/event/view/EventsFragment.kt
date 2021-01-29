package com.besugos.marveluniverse.event.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.view.CharactersComicsAdapter
import com.besugos.marveluniverse.character.view.CharactersEventsAdapter
import com.besugos.marveluniverse.event.model.EventModel
import com.besugos.marveluniverse.event.repository.EventRepository
import com.besugos.marveluniverse.event.viewmodel.EventViewModel
import com.besugos.marveluniverse.home.model.CharacterSummaryModel
import com.besugos.marveluniverse.home.model.ComicSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso


class EventsFragment : Fragment() {

    private lateinit var _adapter: EventAdapter
    private lateinit var _viewModel: EventViewModel
    private lateinit var _view: View
    private lateinit var characterAdapter: EventCharactersAdapter
    private lateinit var comicsAdapter: EventComicAdapter

    private var _searchByName: String? = null
    private var _totalItemCountAux = 0
    private var _wasTheLastPageReturned = false


    private var _listEvent = mutableListOf<EventModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_events, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _view = view
        initialSearch()
        showLoading(true)
        initSearchView()
        setScrollView()

    }

    private fun initialSearch() {
        val recyclerView = _view.findViewById<RecyclerView>(R.id.recyclerEvent)
        val manager = LinearLayoutManager(_view.context)

        _listEvent = mutableListOf()
        _adapter = EventAdapter(_listEvent) {
            createModal(it)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }

        _viewModel = ViewModelProvider(
            this,
            EventViewModel.EventViewModelFactory(EventRepository())
        ).get(EventViewModel::class.java)

        _viewModel.getEvents().observe(viewLifecycleOwner, Observer {
            showResult(it)
        })

    }

    private fun showResult(list: List<EventModel>?) {
        showLoading(false)
        list?.let { _listEvent.addAll(it) }
        listNotFound(_listEvent.isEmpty())
        _adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    private fun initSearchView() {
        val searchEvent = _view.findViewById<SearchView>(R.id.searchViewEvent)

        searchEvent.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                searchEvent.clearFocus()
                _searchByName = query

                _viewModel.getEvents(_searchByName).observe(viewLifecycleOwner, Observer {
                    _listEvent.clear()
                    showResult(it)
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _listEvent.clear()
                    _searchByName = null
                    showResult(_viewModel.getLocalEvents())
                }

                return true
            }
        })
    }

    private fun setScrollView() {
        _view.findViewById<RecyclerView>(R.id.recyclerEvent).addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val target = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = target!!.itemCount
                val lastVisible = target.findLastVisibleItemPosition()
                val lastItem = lastVisible + 5 >= totalItemCount

                if (_wasTheLastPageReturned) _wasTheLastPageReturned =
                    _totalItemCountAux == totalItemCount

                if (totalItemCount > 0 && lastItem && !_wasTheLastPageReturned) {
                    _wasTheLastPageReturned = true
                    _totalItemCountAux = totalItemCount
                    _viewModel.nextPage(_searchByName).observe(viewLifecycleOwner, Observer {
                        showResult(it)
                    })
                }

            }
        })
    }

    private fun listNotFound(notFound: Boolean) {
        if (notFound) {
            _view.findViewById<View>(R.id.layoutNotFound).visibility = View.VISIBLE
        } else {
            _view.findViewById<View>(R.id.layoutNotFound).visibility = View.GONE
        }
    }

    private fun createModal(event: EventModel) {
        val inflater = _view.context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutView = inflater.inflate(R.layout.events_detail, null)
        val alertaDialog = BottomSheetDialog(_view.context)

        val eventName = layoutView.findViewById<TextView>(R.id.txtNameEventDetails)

        eventName.text = event.title

        val eventDescription = layoutView.findViewById<TextView>(R.id.txtDescriptionEventDetails)

        if (event.description.isNullOrEmpty()) {
            eventDescription.text = "Oops, no description for this hero :("
        } else {
            eventDescription.text = event.description
        }

        val imgUrl = event.thumbnail!!.getThumb("standard_fantastic")

        val eventImage = layoutView.findViewById<ImageView>(R.id.imgAvatarEventDetails)

        Picasso.get()
            .load(imgUrl)
            .into(eventImage)

        val duration = layoutView.findViewById<TextView>(R.id.txtEventYearDetails)

        if (event.start.isNullOrEmpty() || event.end.isNullOrEmpty()) {
            duration.text = layoutView.context.getString(R.string.ops_not_available)
        } else {
            val startDate = event.start?.substring(0, 7)
            val endDate = event.end?.substring(0, 7)
            duration.text = "${startDate} - ${endDate}"
        }

        val recyclerViewCharacters =
            layoutView.findViewById<RecyclerView>(R.id.eventDetailsCharacterList)
        val charactersManager =
            LinearLayoutManager(alertaDialog.context, LinearLayoutManager.HORIZONTAL, false)

        val listCharacters = mutableListOf<CharacterSummaryModel>()
        val characterDetails = event.characters?.items
        characterDetails?.forEach() {
            listCharacters.add(it)
        }

        characterAdapter = EventCharactersAdapter(listCharacters)

        recyclerViewCharacters?.apply {
            setHasFixedSize(true)
            layoutManager = charactersManager
            adapter = characterAdapter
        }

        val recyclerViewComics =
            layoutView.findViewById<RecyclerView>(R.id.eventDetailsComicList)
        val comicsManager =
            LinearLayoutManager(alertaDialog.context, LinearLayoutManager.HORIZONTAL, false)

        val listComics = mutableListOf<ComicSummaryModel>()
        val comicDetails = event.comics?.items
        comicDetails?.forEach() {
            listComics.add(it)
        }

        comicsAdapter = EventComicAdapter(listComics)

        recyclerViewComics?.apply {
            setHasFixedSize(true)
            layoutManager = comicsManager
            adapter = comicsAdapter
        }

        alertaDialog.apply {
            setContentView(layoutView)
            show()
        }

    }
}

