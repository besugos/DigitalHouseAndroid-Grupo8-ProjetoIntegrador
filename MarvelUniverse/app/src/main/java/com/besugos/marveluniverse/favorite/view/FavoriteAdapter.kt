package com.besugos.marveluniverse.favorite.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.besugos.marveluniverse.R
import com.besugos.marveluniverse.favorite.model.FavoriteModel

class FavoriteAdapter(
    private var favorites: MutableList<FavoriteModel>,
    private val listener: (FavoriteModel) -> Unit
) :
    RecyclerView.Adapter<FavoriteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_favorite_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount() = favorites.size

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val favorite = favorites[position]
        holder.bind(favorite)
        holder.itemView.setOnClickListener { listener(favorite) }
    }

}