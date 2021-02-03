package com.besugos.marveluniverse.comic.view

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.comic.repository.ComicRepository
import com.besugos.marveluniverse.comic.viewmodel.ComicViewModel
import com.besugos.marveluniverse.home.model.CharacterSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso


class ComicsFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: ComicViewModel
    private lateinit var _adapter: ComicAdapter

    private var _comics = mutableListOf<ComicModel>()
    private var _searchByName: String? = null
    private var _totalItemCountAux = 0
    private var _wasTheLastPageReturned = false
    private lateinit var _comic: ComicModel
    private lateinit var eventsAdapter: ComicEventsAdapter
    private lateinit var charactersAdapter: ComicCharactersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_comics, container, false)
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
        val recyclerView = _view.findViewById<RecyclerView>(R.id.recyclerComic)
        val manager = LinearLayoutManager(_view.context)

        _comics = mutableListOf()
        _adapter = ComicAdapter(_comics) {
            val intent = Intent(this.context, ComicDetails::class.java)
//            val bundle = bundleOf("COMIC" to it as ComicModel)
            intent.putExtra("Comic", it)
            startActivity(intent)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }

        _viewModel = ViewModelProvider(
            this,
            ComicViewModel.ComicViewModelFactory(ComicRepository())
        ).get(ComicViewModel::class.java)

        _viewModel.getComics().observe(viewLifecycleOwner, Observer {
            showResult(it)
        })

    }

    private fun showResult(list: List<ComicModel>?) {
        showLoading(false)
        list?.let { _comics.addAll(it) }
        listNotFound(_comics.isEmpty())
        _adapter.notifyDataSetChanged()
    }

    private fun showLoading(isLoading: Boolean) {
        val viewLoading = _view.findViewById<View>(R.id.loadingComic)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    private fun initSearchView() {
        val searchCharacter = _view.findViewById<SearchView>(R.id.searchComic)

        searchCharacter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                searchCharacter.clearFocus()
                _searchByName = query

                _viewModel.getComics(_searchByName).observe(viewLifecycleOwner, Observer {
                    _comics.clear()
                    showResult(it)
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _comics.clear()
                    _searchByName = null
                    showResult(_viewModel.getLocalComics())
                }

                return true
            }
        })
    }

    private fun setScrollView() {
        _view.findViewById<RecyclerView>(R.id.recyclerComic).addOnScrollListener(object :
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
            _view.findViewById<View>(R.id.layoutNotFoundComic).visibility = View.VISIBLE
        } else {
            _view.findViewById<View>(R.id.layoutNotFoundComic).visibility = View.GONE
        }
    }

    private fun createModal(_comic: ComicModel) {
        val inflater =
            _view.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutView = inflater.inflate(R.layout.comic_detail, null)
        val alertDialog = BottomSheetDialog(_view.context)

        val txtTitleComicDetail = layoutView.findViewById<TextView>(R.id.txtTitleComicDetail)
        val imgComicDetail = layoutView.findViewById<ImageView>(R.id.imgComicDetail)
        val txtDescriptionComicDetail =
            layoutView.findViewById<TextView>(R.id.txtDescriptionComicDetail)

        txtTitleComicDetail.text = _comic.title

        Picasso.get()
            .load(_comic.thumbnail?.getThumb(IMG_RESOLUTION_FANTASTIC))
            .into(imgComicDetail)

        val recyclerViewEvents = layoutView.findViewById<RecyclerView>(R.id.comicDetailsEventList)
        val eventsManager =
            LinearLayoutManager(alertDialog.context, LinearLayoutManager.HORIZONTAL, false)

        val listEvents = mutableListOf<EventSummaryModel>()
        val eventsDetails = _comic.events?.items
        eventsDetails?.forEach() {
            listEvents.add(it)
        }

        eventsAdapter = ComicEventsAdapter(listEvents)

        recyclerViewEvents?.apply {
            setHasFixedSize(true)
            layoutManager = eventsManager
            adapter = eventsAdapter
        }

        val recyclerViewCharacters =
            layoutView.findViewById<RecyclerView>(R.id.comicDetailsCharacterList)
        val charactersManager =
            LinearLayoutManager(alertDialog.context, LinearLayoutManager.HORIZONTAL, false)

        val listCharacters = mutableListOf<CharacterSummaryModel>()
        val charactersDetails = _comic.characters?.items
        charactersDetails?.forEach() {
            listCharacters.add(it)
        }

        charactersAdapter = ComicCharactersAdapter(listCharacters)

        recyclerViewCharacters?.apply {
            setHasFixedSize(true)
            layoutManager = charactersManager
            adapter = charactersAdapter
        }

        alertDialog.apply {
            setContentView(layoutView)
            show()
        }



        if (_comic.description.isNullOrEmpty()) {
            txtDescriptionComicDetail.text =
                _view.context.getString(R.string.comic_description_not_found)
        } else {
            txtDescriptionComicDetail.text = _comic.description
        }

    }

}