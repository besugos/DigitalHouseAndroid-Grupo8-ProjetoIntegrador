package com.besugos.marveluniverse.character.repository

import com.besugos.marveluniverse.data.api.AuthHashUtils
import com.besugos.marveluniverse.data.api.NetworkUtils
import com.besugos.marveluniverse.data.model.ResponseModel
import com.besugos.marveluniverse.character.model.CharacterModel
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface CharacterEndpoint {

    @GET("characters")
    suspend fun getCharacters(
        @Query("nameStartsWith") nameStartsWith: String? = "",
        @Query("offset") offset: Int? = 0,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<CharacterModel>

    @GET("characters/{id}")
    suspend fun getCharacterById(
        @Path("id") id: Int,
        @Query("ts") timeStamp: String? = AuthHashUtils.getTimestamp(),
        @Query("apikey") apiKey: String? = AuthHashUtils.PUBLIC_KEY,
        @Query("hash") hash: String? = AuthHashUtils.getHash()
    ): ResponseModel<CharacterModel>

    companion object {
        val endpoint: CharacterEndpoint by lazy {
            NetworkUtils.getRetrofitInstance().create(CharacterEndpoint::class.java)
        }
    }
}