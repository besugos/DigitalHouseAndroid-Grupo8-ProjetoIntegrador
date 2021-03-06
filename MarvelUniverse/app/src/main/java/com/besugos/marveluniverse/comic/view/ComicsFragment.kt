package com.besugos.marveluniverse.comic.view

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
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.comic.repository.ComicRepository
import com.besugos.marveluniverse.comic.viewmodel.ComicViewModel


class ComicsFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _viewModel: ComicViewModel
    private lateinit var _adapter: ComicAdapter

    private var _comics = mutableListOf<ComicModel>()
    private var _searchByName: String? = null
    private var _totalItemCountAux = 0
    private var _wasTheLastPageReturned = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comics, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myView = view
        initialSearch()
        showLoading(true)
        offlineUser(false)
        initSearchView()
        setScrollView()

    }

    private fun initialSearch() {
        val recyclerView = _myView.findViewById<RecyclerView>(R.id.recyclerComic)
        val manager = LinearLayoutManager(_myView.context)

        _comics = mutableListOf()
        _adapter = ComicAdapter(_comics) {
            val intent = Intent(this.context, ComicDetails::class.java)
            intent.putExtra("Comic", it)
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
            ComicViewModel.ComicViewModelFactory(ComicRepository())
        ).get(ComicViewModel::class.java)

        _viewModel.getComics().observe(viewLifecycleOwner, Observer {
            if(it != null) showResult(it.data.results)
            else offlineUser(true)
        })

    }

    private fun showResult(list: List<ComicModel>?) {
        showLoading(false)
        list?.let { _comics.addAll(it) }
        listNotFound(_comics.isEmpty())
        _adapter.notifyDataSetChanged()
        offlineUser(false)
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _myView.findViewById<View>(R.id.loadingComic)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    private fun initSearchView() {
        val searchCharacter = _myView.findViewById<SearchView>(R.id.searchComic)

        searchCharacter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                searchCharacter.clearFocus()
                _searchByName = query

                _viewModel.getComics(_searchByName).observe(viewLifecycleOwner, Observer {
                    _comics.clear()
                    if(it != null) showResult(it.data.results)
                    else offlineUser(true)
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _comics.clear()
                    _searchByName = null
                    if(_myView.findViewById<LinearLayout>(R.id.layoutNotNetwork).visibility == View.GONE) {
                        showResult(_viewModel.getLocalComics())
                    }
                }

                return true
            }
        })
    }

    private fun setScrollView() {
        _myView.findViewById<RecyclerView>(R.id.recyclerComic).addOnScrollListener(object :
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
            _myView.findViewById<View>(R.id.layoutNotFoundComic).visibility = View.VISIBLE
        } else {
            _myView.findViewById<View>(R.id.layoutNotFoundComic).visibility = View.GONE
        }
    }

    private fun offlineUser(isOffline: Boolean) {
        _myView.findViewById<LinearLayout>(R.id.layoutNotNetwork).visibility = if(isOffline){
            View.VISIBLE
        } else View.GONE
        showLoading(false)
        listNotFound(false)
    }

}