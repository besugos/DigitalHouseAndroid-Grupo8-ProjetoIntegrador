package com.besugos.marveluniverse.character.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.repository.CharacterRepository
import com.besugos.marveluniverse.character.viewmodel.CharacterViewModel
import com.besugos.marveluniverse.character.model.CharacterModel


class CharactersFragment : Fragment() {

    private lateinit var _view: View
    private lateinit var _viewModel: CharacterViewModel
    private lateinit var _adapter: CharactersAdapter

    private var _listCharacters = mutableListOf<CharacterModel>()
    private var _searchByName: String? = null
    private var _totalItemCountAux = 0
    private var _wasTheLastPageReturned = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
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
        val recyclerView = _view.findViewById<RecyclerView>(R.id.recyclerCharacter)
        val manager = LinearLayoutManager(_view.context)

        _listCharacters = mutableListOf()
        _adapter =
            CharactersAdapter(
                _listCharacters
            )

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }

        _viewModel = ViewModelProvider(
            this,
            CharacterViewModel.CharacterViewModelFactory(CharacterRepository())
        ).get(CharacterViewModel::class.java)

        _viewModel.getCharacters().observe(viewLifecycleOwner, Observer {
            showResult(it)
        })

    }

    private fun showResult(list: List<CharacterModel>?) {
        showLoading(false)
        list?.let { _listCharacters.addAll(it) }
        listNotFound(_listCharacters.isEmpty())
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
        val searchCharacter = _view.findViewById<SearchView>(R.id.searchCharacter)

        searchCharacter.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                searchCharacter.clearFocus()
                _searchByName = query

                _viewModel.getCharacters(_searchByName).observe(viewLifecycleOwner, Observer {
                    _listCharacters.clear()
                    showResult(it)
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _listCharacters.clear()
                    _searchByName = null
                    showResult(_viewModel.getLocalCharacters())
                }

                return true
            }
        })
    }

    private fun setScrollView() {
        _view.findViewById<RecyclerView>(R.id.recyclerCharacter).addOnScrollListener(object :
            RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val target = recyclerView.layoutManager as LinearLayoutManager?
                val totalItemCount = target!!.itemCount
                val lastVisible = target.findLastVisibleItemPosition()
                val lastItem = lastVisible + 5 >= totalItemCount

                if(_wasTheLastPageReturned) _wasTheLastPageReturned = _totalItemCountAux == totalItemCount

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

}