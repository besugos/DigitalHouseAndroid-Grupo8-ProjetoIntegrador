package com.besugos.marveluniverse.favorite.repository

import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.room.FavoriteDAO

class FavoriteRepository(private val favoriteDAO: FavoriteDAO) {

    suspend fun insertFavorite(favorite: FavoriteModel) = favoriteDAO.insertFavorite(favorite)

    suspend fun removeFavorite(favorite: FavoriteModel) = favoriteDAO.removeFavorite(favorite)

    suspend fun getFavorites() = favoriteDAO.getFavorites()

    suspend fun getFavoritesByName(nameStartsWith: String?) = favoriteDAO.getFavoritesByName(nameStartsWith)

    suspend fun getFavoriteById(favoriteId: Int) = favoriteDAO.getFavoriteById(favoriteId)

}