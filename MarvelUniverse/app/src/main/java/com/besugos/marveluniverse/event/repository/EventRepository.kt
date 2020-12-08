package com.besugos.marveluniverse.event.repository

class EventRepository {

    private val client = EventEndpoint.endpoint

    suspend fun getEvents(search: String? = null, offset: Int? = 0) = client.getEvents(search, offset)

    suspend fun getEventById(id: Int) = client.getEventById(id)
}