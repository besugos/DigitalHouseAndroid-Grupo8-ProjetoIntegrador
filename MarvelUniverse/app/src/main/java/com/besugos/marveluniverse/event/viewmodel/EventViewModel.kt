package com.besugos.marveluniverse.event.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.besugos.marveluniverse.event.model.EventModel
import com.besugos.marveluniverse.event.repository.EventRepository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class EventViewModel(
    private val repository: EventRepository
): ViewModel() {

    private val _events = mutableListOf<EventModel>()
    private var _offset = 0
    private var _hasNextPage = true
    private var _hasNextPageSearchByName = true


    fun getEvents(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        val response = repository.getEvents(search)
        _offset = response.data.offset + response.data.count

        if(isSearchByName){
            _hasNextPageSearchByName = _offset < response.data.total
        } else {
            _hasNextPage = _offset < response.data.total
            _events.addAll(response.data.results)
        }

        emit(response.data.results)
    }

    fun nextPage(search: String? = null) = liveData(Dispatchers.IO) {
        val isSearchByName = !search.isNullOrEmpty()
        if( (isSearchByName && _hasNextPageSearchByName) || (!isSearchByName && _hasNextPage) ) {

            val response = repository.getEvents(search, _offset)
            _offset = response.data.offset + response.data.count

            if(isSearchByName){
                _hasNextPageSearchByName = _offset < response.data.total
            } else {
                _hasNextPage = _offset < response.data.total
                _events.addAll(response.data.results)
            }

            emit(response.data.results)
        }
    }

    fun getLocalEvents(): List<EventModel> {
        _offset = _events.size
        return _events
    }

    fun getEventById(id: Int) = liveData(Dispatchers.IO) {
        val response = repository.getEventById(id)
        emit(response.data.results)
    }

    class EventViewModelFactory(
        private val repository: EventRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return EventViewModel(
                repository
            ) as T
        }
    }
}