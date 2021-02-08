package com.besugos.marveluniverse.event.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.event.model.EventModel
import com.besugos.marveluniverse.event.repository.EventRepository
import com.besugos.marveluniverse.event.viewmodel.EventViewModel


class EventsFragment : Fragment() {

    private lateinit var _adapter: EventAdapter
    private lateinit var _viewModel: EventViewModel
    private lateinit var _view: View

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
            val intent = Intent(this.context, EventDetailsActivity::class.java)
            intent.putExtra("Event", it)
            startActivity(intent)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
            adapter = _adapter
        }

        _viewModel = ViewModelProvider(
            this,
            EventViewModel.EventViewModelFactory(EventRepository())
        ).get(EventViewModel::class.java)

        _viewModel.getEvents().observe(viewLifecycleOwner, Observer {
            if(it != null) showResult(it.data.results)
            else offlineUser(true)
        })

    }

    private fun showResult(list: List<EventModel>?) {
        showLoading(false)
        list?.let { _listEvent.addAll(it) }
        listNotFound(_listEvent.isEmpty())
        _adapter.notifyDataSetChanged()
        offlineUser(false)
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
                    if(it != null) showResult(it.data.results)
                    else offlineUser(true)
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
                        if(it != null) showResult(it.data.results)
                        else offlineUser(true)
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

    private fun offlineUser(isOffline: Boolean) {
        _view.findViewById<LinearLayout>(R.id.layoutNotNetwork).visibility = if(isOffline){
            View.VISIBLE
        } else View.GONE
        showLoading(false)
    }
}

