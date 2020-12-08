package com.besugos.marveluniverse.story.repository

class StoryRepository {

    private val client = StoryEndpoint.endpoint

    suspend fun getStories(offset: Int? = 0) = client.getStories(offset)

    suspend fun getStoryById(id: Int) = client.getStoryById(id)
}