package com.besugos.marveluniverse.comic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.besugos.marveluniverse.character.model.CharacterModel
import com.besugos.marveluniverse.character.repository.CharacterRepository
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.comic.repository.ComicRepository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class ComicViewModel(
    private val repository: ComicRepository
): ViewModel() {

    private val _comics = mutableListOf<ComicModel>()
    private var _offset = 0
    private var _hasNextPage = true
    private var _hasNextPageSearchByName = true


    fun getComics(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        val response = repository.getComics(search)
        _offset = response.data.offset + response.data.count

        if(isSearchByName){
            _hasNextPageSearchByName = _offset < response.data.total
        } else {
            _hasNextPage = _offset < response.data.total
            _comics.addAll(response.data.results)
        }

        emit(response.data.results)
    }

    fun nextPage(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        if( (isSearchByName && _hasNextPageSearchByName) || (!isSearchByName && _hasNextPage) ) {

            val response = repository.getComics(search, _offset)
            _offset = response.data.offset + response.data.count

            if(isSearchByName){
                _hasNextPageSearchByName = _offset < response.data.total
            } else {
                _hasNextPage = _offset < response.data.total
                _comics.addAll(response.data.results)
            }

            emit(response.data.results)
        }
    }

    fun getLocalComics(): List<ComicModel> {
        _offset = _comics.size
        return _comics
    }

    fun getComicById(id: Int) = liveData(Dispatchers.IO) {
        val response = repository.getComicById(id)
        emit(response.data.results)
    }

    class ComicViewModelFactory(
        private val repository: ComicRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return ComicViewModel(
                repository
            ) as T
        }
    }
}