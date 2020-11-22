package com.besugos.marveluniverse.home.repository

class EventRepository {
    private val client = EventEndpoint.endpoint

    suspend fun getEvents(ts: String?, apiKey: String?, hash: String?) = client.getEvents(ts, apiKey, hash)
}