package com.besugos.marveluniverse.story.repository

import com.besugos.marveluniverse.data.api.AuthHashUtils
import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import com.besugos.marveluniverse.story.model.StoryModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface StoryEndpoint {

    @GET("stories")
    suspend fun getStories(
        @Query("offset") offset: Int? = 0,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<StoryModel>

    @GET("stories/{id}")
    suspend fun getStoryById(
        @Path("id") id: Int,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<StoryModel>

    companion object {
        val endpoint: StoryEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(StoryEndpoint::class.java)
        }
    }
}