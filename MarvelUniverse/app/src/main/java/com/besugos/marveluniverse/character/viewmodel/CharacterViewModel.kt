package com.besugos.marveluniverse.character.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.besugos.marveluniverse.character.model.CharacterModel
import com.besugos.marveluniverse.character.repository.CharacterRepository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class CharacterViewModel(private val repository: CharacterRepository): ViewModel() {

    private val _characters = mutableListOf<CharacterModel>()
    private var _offset = 0
    private var _hasNextPage = true
    private var _hasNextPageSearchByName = true


    fun getCharacters(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        val response = repository.getCharacters(search)
        _offset = response.data.offset + response.data.count

        if(isSearchByName){
            _hasNextPageSearchByName = _offset < response.data.total
        } else {
            _hasNextPage = _offset < response.data.total
            _characters.addAll(response.data.results)
        }

        emit(response.data.results)
    }

    fun nextPage(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        if( (isSearchByName && _hasNextPageSearchByName) || (!isSearchByName && _hasNextPage) ) {

            val response = repository.getCharacters(search, _offset)
            _offset = response.data.offset + response.data.count

            if(isSearchByName){
                _hasNextPageSearchByName = _offset < response.data.total
            } else {
                _hasNextPage = _offset < response.data.total
                _characters.addAll(response.data.results)
            }

            emit(response.data.results)
        }
    }

    fun getLocalCharacters(): List<CharacterModel> {
        _offset = _characters.size
        return _characters
    }

    fun getCharacterById(id: Int) = liveData(Dispatchers.IO) {
        val response = repository.getCharacterById(id)
        emit(response.data.results)
    }

    class CharacterViewModelFactory(
        private val repository: CharacterRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return CharacterViewModel(
                repository
            ) as T
        }
    }
}