package com.besugos.marveluniverse.comic.repository

class ComicRepository {

    private val client = ComicEndpoint.endpoint

    suspend fun getComics(search: String? = null, offset: Int? = 0) = client.getComics(search, offset)

    suspend fun getComicById(id: Int) = client.getComicById(id)
}