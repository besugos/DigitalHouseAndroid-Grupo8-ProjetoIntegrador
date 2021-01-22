package com.besugos.marveluniverse.favorite.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.repository.FavoriteRepository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class FavoriteViewModel(private val repository: FavoriteRepository): ViewModel() {

    fun insertFavorite(favorite: FavoriteModel) = liveData(Dispatchers.IO) {
        repository.insertFavorite(favorite)
        emit(true)
    }

    fun removeFavorite(favorite: FavoriteModel) = liveData(Dispatchers.IO) {
        repository.removeFavorite(favorite)
        emit(true)
    }

    fun getFavorites() = liveData(Dispatchers.IO) {
        val favorites = repository.getFavorites()
        emit(favorites)
    }

    fun getFavoritesByName(nameStartsWith: String? = "") = liveData(Dispatchers.IO) {
        val favorites = repository.getFavoritesByName(nameStartsWith)
        emit(favorites)
    }

    fun getFavoriteById(favoriteId: Int) = liveData(Dispatchers.IO) {
        val level = repository.getFavoriteById(favoriteId)
        emit(level)
    }

    class FavoriteViewModelFactory(private val repository: FavoriteRepository): ViewModelProvider.Factory {

        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return FavoriteViewModel(repository) as T
        }

    }

}