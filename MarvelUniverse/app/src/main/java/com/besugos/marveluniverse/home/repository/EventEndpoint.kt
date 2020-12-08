package com.besugos.marveluniverse.home.repository

import android.util.EventLog
import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import com.besugos.marveluniverse.home.model.CharacterModel
import com.besugos.marveluniverse.home.model.EventModel
import retrofit2.http.GET
import retrofit2.http.Query

interface EventEndpoint {
    @GET("events")
    suspend fun getEvents(@Query("ts") timeStamp: String?, @Query("apikey") apiKey: String?, @Query("hash") hash: String?): ResponseModel<EventModel>

    companion object {
        val endpoint: EventEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(EventEndpoint::class.java)
        }
    }
}