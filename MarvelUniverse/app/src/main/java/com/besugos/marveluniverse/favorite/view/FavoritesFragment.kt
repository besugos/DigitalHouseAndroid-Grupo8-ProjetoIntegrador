package com.besugos.marveluniverse.favorite.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.data.room.MyDataBase
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.repository.FavoriteRepository
import com.besugos.marveluniverse.favorite.viewmodel.FavoriteViewModel
import com.besugos.marveluniverse.favorite.viewmodel.SharedViewModel
import com.google.firebase.auth.FirebaseAuth

class FavoritesFragment : Fragment() {

    private lateinit var _userId: String
    private lateinit var _myView: View
    private lateinit var _adapter: FavoriteAdapter

    private lateinit var _favoriteViewModel: FavoriteViewModel
    private val _sharedViewModel: SharedViewModel by activityViewModels()

    private var _listFavorites = mutableListOf<FavoriteModel>()
    private var _searchByName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_characters, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _myView = view
        _userId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
        initialSearch()
        initSearchView()
        favoritesInsertListener()

    }

    private fun initialSearch() {
        val recyclerView = _myView.findViewById<RecyclerView>(R.id.recyclerCharacter)
        val manager = LinearLayoutManager(_myView.context)

        _listFavorites = mutableListOf()
        _adapter = FavoriteAdapter(_listFavorites){
            val intent = Intent(this.context, FavoriteDetailActivity::class.java)
            intent.putExtra("Favorite", it)
            startActivity(intent)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
            adapter = _adapter
        }

        _favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                FavoriteRepository(MyDataBase.getDataBaseClient(_myView.context).favoriteDAO())
            )
        ).get(FavoriteViewModel::class.java)

        _favoriteViewModel.getFavorites(_userId).observe(viewLifecycleOwner, Observer {
            _listFavorites.clear()
            showResult(it)
        })

        _myView.findViewById<View>(R.id.loading).visibility = View.GONE

    }

    private fun showResult(list: List<FavoriteModel>?) {
        list?.let { _listFavorites.addAll(it) }
        listNotFound(_listFavorites.isEmpty())
        _adapter.notifyDataSetChanged()
    }

    private fun initSearchView() {
        val searchCharacter = _myView.findViewById<SearchView>(R.id.searchCharacter)

        searchCharacter.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchCharacter.clearFocus()
                _searchByName = "$query%"

                _favoriteViewModel.getFavoritesByName(_userId, _searchByName).observe(viewLifecycleOwner, Observer {
                    _listFavorites.clear()
                    showResult(it)
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _listFavorites.clear()
                    _searchByName = null
                    _favoriteViewModel.getFavorites(_userId).observe(
                        viewLifecycleOwner, Observer {
                            _listFavorites.clear()
                            showResult(it)
                        }
                    )
                }

                return true
            }
        })
    }

    private fun listNotFound(notFound: Boolean) {
        if (notFound) {
            _myView.findViewById<View>(R.id.layoutNotFound).visibility = View.VISIBLE
        } else {
            _myView.findViewById<View>(R.id.layoutNotFound).visibility = View.GONE
        }
    }

    private fun favoritesInsertListener() {
        _sharedViewModel.flag.observe(viewLifecycleOwner, Observer {

            if(_searchByName == null) {
                _favoriteViewModel.getFavorites(_userId).observe(
                    viewLifecycleOwner, Observer {
                        _listFavorites.clear()
                        showResult(it)
                    }
                )
            } else {
                _favoriteViewModel.getFavoritesByName(_userId, _searchByName).observe(
                    viewLifecycleOwner, Observer {
                        _listFavorites.clear()
                        showResult(it)
                    }
                )
            }

        })
    }

}