package com.besugos.marveluniverse.character.repository

class CharacterRepository {

    private val client = CharacterEndpoint.endpoint

    suspend fun getCharacters(search: String? = null, offset: Int? = 0) = client.getCharacters(search, offset)

    suspend fun getCharacterById(id: Int) = client.getCharacterById(id)

}