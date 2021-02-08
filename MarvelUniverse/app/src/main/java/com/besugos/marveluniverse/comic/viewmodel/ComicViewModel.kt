package com.besugos.marveluniverse.comic.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.comic.repository.ComicRepository
import com.besugos.marveluniverse.data.model.ResponseModel
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
        var response: ResponseModel<ComicModel>? = null
        try {
            response = repository.getComics(search)
            _offset = response.data.offset + response.data.count

            if(isSearchByName){
                _hasNextPageSearchByName = _offset < response.data.total
            } else {
                _hasNextPage = _offset < response.data.total
                _comics.addAll(response.data.results)
            }
        } catch(e: Exception){}

        emit(response)
    }

    fun nextPage(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        var response: ResponseModel<ComicModel>? = null
        if( (isSearchByName && _hasNextPageSearchByName) || (!isSearchByName && _hasNextPage) ) {
            try{

                response = repository.getComics(search, _offset)
                _offset = response.data.offset + response.data.count

                if(isSearchByName){
                    _hasNextPageSearchByName = _offset < response.data.total
                } else {
                    _hasNextPage = _offset < response.data.total
                    _comics.addAll(response.data.results)
                }
            } catch(e: Exception){}
            emit(response)
        }
    }

    fun getLocalComics(): List<ComicModel> {
        _offset = _comics.size
        return _comics
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