package com.besugos.marveluniverse.character.view

import android.annotation.SuppressLint
import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.character.repository.CharacterRepository
import com.besugos.marveluniverse.character.viewmodel.CharacterViewModel
import com.besugos.marveluniverse.character.model.CharacterModel
import com.besugos.marveluniverse.data.room.MyDataBase
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.repository.FavoriteRepository
import com.besugos.marveluniverse.favorite.viewmodel.FavoriteViewModel
import com.besugos.marveluniverse.home.model.ComicSummaryModel
import com.besugos.marveluniverse.home.model.EventSummaryModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.squareup.picasso.Picasso


class CharactersFragment : Fragment() {

    private lateinit var _myView: View
    private lateinit var _characterViewModel: CharacterViewModel
    private lateinit var _adapter: CharactersAdapter
    private lateinit var eventsAdapter: CharactersEventsAdapter
    private lateinit var comicsAdapter: CharactersComicsAdapter

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

        _myView = view
        initialSearch()
        showLoading(true)
        initSearchView()
        setScrollView()

    }

    private fun initialSearch() {
        val recyclerView = _myView.findViewById<RecyclerView>(R.id.recyclerCharacter)
        val manager = LinearLayoutManager(_myView.context)

        _listCharacters = mutableListOf()
        _adapter = CharactersAdapter(_listCharacters) {
            createModal(it)
        }

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }

        _characterViewModel = ViewModelProvider(
            this,
            CharacterViewModel.CharacterViewModelFactory(CharacterRepository())
        ).get(CharacterViewModel::class.java)

        _characterViewModel.getCharacters().observe(viewLifecycleOwner, Observer {
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
        val viewLoading = _myView.findViewById<View>(R.id.loading)

        if (isLoading) {
            viewLoading.visibility = View.VISIBLE
        } else {
            viewLoading.visibility = View.GONE
        }
    }

    private fun initSearchView() {
        val searchCharacter = _myView.findViewById<SearchView>(R.id.searchCharacter)

        searchCharacter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                showLoading(true)
                searchCharacter.clearFocus()
                _searchByName = query

                _characterViewModel.getCharacters(_searchByName)
                    .observe(viewLifecycleOwner, Observer {
                        _listCharacters.clear()
                        showResult(it)
                    })

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText.isNullOrEmpty()) {
                    _listCharacters.clear()
                    _searchByName = null
                    showResult(_characterViewModel.getLocalCharacters())
                }

                return true
            }
        })
    }

    private fun setScrollView() {
        _myView.findViewById<RecyclerView>(R.id.recyclerCharacter).addOnScrollListener(object :
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
                    _characterViewModel.nextPage(_searchByName)
                        .observe(viewLifecycleOwner, Observer {
                            showResult(it)
                        })
                }

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

    private fun createModal(character: CharacterModel) {
        val inflater =
            _myView.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val layoutView = inflater.inflate(R.layout.character_detail, null)
        val modal = BottomSheetDialog(_myView.context)

        val characterName = layoutView.findViewById<TextView>(R.id.txtNameCharacterDetails)
        characterName.text = character.name

        val characterDescription =
            layoutView.findViewById<TextView>(R.id.txtDescriptionCharacterDetails)
        characterDescription.text =
            if (character.description.isNullOrEmpty()) _myView.context.getText(R.string.character_description_not_found)
            else character.description

        val imgHero = layoutView.findViewById<ImageView>(R.id.imgAvatarCharacterDetails)
        Picasso.get()
            .load(character.thumbnail!!.getThumb("standard_fantastic"))
            .into(imgHero)

        val recyclerViewEvents =
            layoutView.findViewById<RecyclerView>(R.id.characterDetailsEventsList)
        val eventsManager =
            LinearLayoutManager(modal.context, LinearLayoutManager.HORIZONTAL, false)

        val listEvents = mutableListOf<EventSummaryModel>()
        val eventsDetails = character.events?.items
        eventsDetails?.forEach() {
            listEvents.add(it)
        }

        if (listEvents.isNullOrEmpty()) {
            val txtEvent = layoutView.findViewById<TextView>(R.id.txtEventCharacterDetails)
            txtEvent.visibility = View.GONE
        }

        eventsAdapter = CharactersEventsAdapter(listEvents)

        recyclerViewEvents?.apply {
            setHasFixedSize(true)
            layoutManager = eventsManager
            adapter = eventsAdapter
        }

        val recyclerViewComics =
            layoutView.findViewById<RecyclerView>(R.id.characterDetailsComicsList)
        val comicsManager =
            LinearLayoutManager(modal.context, LinearLayoutManager.HORIZONTAL, false)

        val listComics = mutableListOf<ComicSummaryModel>()
        val comicsDetails = character.comics?.items
        comicsDetails?.forEach() {
            listComics.add(it)
        }

        if (listComics.isNullOrEmpty()) {
            val txtComic = layoutView.findViewById<TextView>(R.id.txtStoryCharacterDetails)
            txtComic.visibility = View.GONE
        }

        comicsAdapter = CharactersComicsAdapter(listComics)

        recyclerViewComics?.apply {
            setHasFixedSize(true)
            layoutManager = comicsManager
            adapter = eventsAdapter
        }


        val btnToggleFavorite = layoutView.findViewById<ImageButton>(R.id.btnToggleFavorite)

        val favoriteModel = FavoriteModel(
            character.id!!,
            character.name,
            character.description,
            character.thumbnail.path,
            character.thumbnail.extension
        )

        val favoriteViewModel = ViewModelProvider(
            this,
            FavoriteViewModel.FavoriteViewModelFactory(
                FavoriteRepository(MyDataBase.getDataBaseClient(_myView.context).favoriteDAO())
            )
        ).get(FavoriteViewModel::class.java)
        favoriteViewModel.getFavoriteById(favoriteModel.id).observe(viewLifecycleOwner, Observer {
            character.fav = it != null

            btnToggleFavorite.setBackgroundResource(
                if (character.fav) R.drawable.ic_baseline_favorite_24
                else R.drawable.ic_baseline_favorite_border_24
            )

            modal.apply {
                setContentView(layoutView)
                show()
            }
        })

        btnToggleFavorite.setOnClickListener {
            character.fav = !character.fav
            if (character.fav) {
                favoriteViewModel.insertFavorite(favoriteModel).observe(
                    viewLifecycleOwner, Observer { wasUnlocked ->
                        if (wasUnlocked) {
                            btnToggleFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_24)
                        }
                    }
                )
            } else {
                favoriteViewModel.removeFavorite(favoriteModel).observe(
                    viewLifecycleOwner, Observer { wasUnlocked ->
                        if (wasUnlocked) {
                            btnToggleFavorite.setBackgroundResource(R.drawable.ic_baseline_favorite_border_24)
                        }
                    }
                )
            }
        }
    }

}