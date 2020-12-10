package com.besugos.marveluniverse.comic.repository

import com.besugos.marveluniverse.comic.model.ComicModel
import com.besugos.marveluniverse.data.api.AuthHashUtils
import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ComicEndpoint  {

    @GET("comics")
    suspend fun getComics(
        @Query("titleStartsWith") titleStartsWith: String? = "",
        @Query("offset") offset: Int? = 0,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<ComicModel>

    @GET("comics/{id}")
    suspend fun getComicById(
        @Path("id") id: Int,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<ComicModel>

    companion object {
        val endpoint: ComicEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(ComicEndpoint::class.java)
        }
    }
}