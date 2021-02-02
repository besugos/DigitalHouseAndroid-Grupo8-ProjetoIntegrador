package com.besugos.marveluniverse.favorite.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.data.model.ImageModel
import com.besugos.marveluniverse.data.room.MyDataBase
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.repository.FavoriteRepository
import com.besugos.marveluniverse.favorite.viewmodel.FavoriteViewModel
import com.besugos.marveluniverse.favorite.viewmodel.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso


class FavoritesFragment : Fragment() {

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
        initialSearch()
        initSearchView()
        favoritesInsertListener()

    }

    private fun initialSearch() {
        val recyclerView = _myView.findViewById<RecyclerView>(R.id.recyclerCharacter)
        val manager = LinearLayoutManager(_myView.context)

        _listFavorites = mutableListOf()
        _adapter = FavoriteAdapter(_listFavorites){
            createModal(it)
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

        _favoriteViewModel.getFavorites().observe(viewLifecycleOwner, Observer {
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

                _favoriteViewModel.getFavoritesByName(_searchByName).observe(viewLifecycleOwner, Observer {
                    _listFavorites.clear()
                    showResult(it)
                })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _listFavorites.clear()
                    _searchByName = null
                    _favoriteViewModel.getFavorites().observe(
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
                _favoriteViewModel.getFavorites().observe(
                    viewLifecycleOwner, Observer {
                        _listFavorites.clear()
                        showResult(it)
                    }
                )
            } else {
                _favoriteViewModel.getFavoritesByName(_searchByName).observe(
                    viewLifecycleOwner, Observer {
                        _listFavorites.clear()
                        showResult(it)
                    }
                )
            }

        })
    }

    @SuppressLint("InflateParams")
    private fun createModal(favorite: FavoriteModel) {
        val inflater = _myView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutView = inflater.inflate(R.layout.character_detail, null)
        val modal = BottomSheetDialog(_myView.context)

        val characterName = layoutView.findViewById<TextView>(R.id.txtNameCharacterDetails)
        characterName.text = favorite.name

        val characterDescription = layoutView.findViewById<TextView>(R.id.txtDescriptionCharacterDetails)
        characterDescription.text =
            if (favorite.description.isNullOrEmpty()) _myView.context.getText(R.string.character_description_not_found)
            else favorite.description

        val imgHero = layoutView.findViewById<ImageView>(R.id.imgAvatarCharacterDetails)
        Picasso.get()
            .load(ImageModel(favorite.path!!, favorite.extension!!).getThumb("standard_fantastic"))
            .into(imgHero)

        val btnToggleFavorite = layoutView.findViewById<ImageButton>(R.id.btnToggleFavorite)
        btnToggleFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)

        modal.apply {
            setContentView(layoutView)
            show()
        }

        btnToggleFavorite.setOnClickListener {
            _favoriteViewModel.removeFavorite(favorite).observe(
                viewLifecycleOwner, Observer { wasRemoved ->
                    if(wasRemoved) {
                        btnToggleFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        modal.closeOptionsMenu()
                        if(_searchByName != null) {
                            _favoriteViewModel.getFavoritesByName(_searchByName).observe(viewLifecycleOwner, Observer {
                                _listFavorites.clear()
                                showResult(it)
                                modal.dismiss()
                            })
                        } else {
                            _favoriteViewModel.getFavorites().observe(viewLifecycleOwner, Observer {
                                _listFavorites.clear()
                                showResult(it)
                                modal.dismiss()
                            })
                        }
                    }
                }
            )
        }
    }

}