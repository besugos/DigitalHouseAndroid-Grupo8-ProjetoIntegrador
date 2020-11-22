package com.besugos.marveluniverse.home.repository

import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import com.besugos.marveluniverse.home.model.CharacterModel
import retrofit2.http.GET
import retrofit2.http.Query

interface CharacterEndpoint {

    @GET("characters")
    suspend fun getCharacters(@Query("ts") timeStamp: String?, @Query("apikey") apiKey: String?, @Query("hash") hash: String?): ResponseModel<CharacterModel>

    companion object {
        val endpoint: CharacterEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(CharacterEndpoint::class.java)
        }
    }
}