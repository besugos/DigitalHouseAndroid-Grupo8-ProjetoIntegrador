package com.besugos.marveluniverse.home.repository

class StoryRepository {

    private val client = StoryEndpoint.endpoint

    suspend fun getStories(ts: String?, apiKey: String?, hash: String?) = client.getStories(ts, apiKey, hash)
}