package com.besugos.marveluniverse.event.repository

import com.besugos.marveluniverse.data.api.AuthHashUtils
import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import com.besugos.marveluniverse.event.model.EventModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface EventEndpoint {

    @GET("events")
    suspend fun getEvents(
        @Query("nameStartsWith") nameStartsWith: String? = "",
        @Query("offset") offset: Int? = 0,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<EventModel>

    @GET("events/{id}")
    suspend fun getEventById(
        @Path("id") id: Int,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<EventModel>

    companion object {
        val endpoint: EventEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(EventEndpoint::class.java)
        }
    }
}