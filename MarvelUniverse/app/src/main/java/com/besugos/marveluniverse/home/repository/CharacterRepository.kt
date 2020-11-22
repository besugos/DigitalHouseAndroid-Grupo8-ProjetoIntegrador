package com.besugos.marveluniverse.home.repository

class CharacterRepository {
    private val client = CharacterEndpoint.endpoint

    suspend fun getCharacters(ts: String?, apiKey: String?, hash: String?) = client.getCharacters(ts, apiKey, hash)
}