package com.besugos.marveluniverse.story.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.besugos.marveluniverse.story.repository.StoryRepository
import kotlinx.coroutines.Dispatchers

@Suppress("UNCHECKED_CAST")
class StoryViewModel(
    private val repository: StoryRepository
): ViewModel() {

    private var _offset = 0
    private var _hasNextPage = true


    fun getStories() = liveData(Dispatchers.IO) {
        val response = repository.getStories()
        _offset = response.data.offset + response.data.count
        _hasNextPage = _offset < response.data.total
        emit(response.data.results)
    }

    fun nextPage() = liveData(Dispatchers.IO) {
        if(_hasNextPage) {

            val response = repository.getStories(_offset)
            _offset = response.data.offset + response.data.count
            _hasNextPage = _offset < response.data.total

            emit(response.data.results)
        }
    }

    fun getStoryById(id: Int) = liveData(Dispatchers.IO) {
        val response = repository.getStoryById(id)
        emit(response.data.results)
    }

    class StoryViewModelFactory(
        private val repository: StoryRepository
    ): ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            return StoryViewModel(
                repository
            ) as T
        }
    }
}