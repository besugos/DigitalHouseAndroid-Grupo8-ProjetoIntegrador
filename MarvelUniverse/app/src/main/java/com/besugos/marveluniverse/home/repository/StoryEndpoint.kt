package com.besugos.marveluniverse.home.repository

import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import com.besugos.marveluniverse.home.model.StoryModel
import retrofit2.http.GET
import retrofit2.http.Query

interface StoryEndpoint {
    @GET("stories")
    suspend fun getStories(@Query("ts") timeStamp: String?, @Query("apikey") apiKey: String?, @Query("hash") hash: String?): ResponseModel<StoryModel>

    companion object {
        val endpoint: StoryEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(StoryEndpoint::class.java)
        }
    }
}