package com.besugos.marveluniverse.home.view.favorites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.home.model.FavoriteModel


class FavoritesFragment : Fragment() {

    private lateinit var _adapter: FavoriteAdapter
    private lateinit var _view: View
    private var _listFavorites = mutableListOf<FavoriteModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorites, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createFavorites()

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerFavorite)
        val manager = LinearLayoutManager(view.context)

        _view = view

        _adapter = FavoriteAdapter(requireContext(), _listFavorites)

        recyclerView.apply {
            setHasFixedSize(true)
            layoutManager = manager
            adapter = _adapter
        }
    }

    fun createFavorites(): MutableList<FavoriteModel> {
        _listFavorites.clear()
        for (i in 1..10) {
            val favorite = FavoriteModel(
                i,
                "Favorites  $i"
            )
            _listFavorites.add(favorite)
        }
        return _listFavorites
    }

}