package com.besugos.marveluniverse.favorite.repository

import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.room.FavoriteDAO

class FavoriteRepository(private val favoriteDAO: FavoriteDAO) {

    suspend fun insertFavorite(favorite: FavoriteModel) = favoriteDAO.insertFavorite(favorite)

    suspend fun removeFavorite(favoriteId: Int) = favoriteDAO.removeFavorite(favoriteId)

    suspend fun getFavorites(userId: String) = favoriteDAO.getFavorites(userId)

    suspend fun getFavoritesByName(userId: String, nameStartsWith: String?) = favoriteDAO.getFavoritesByName(userId, nameStartsWith)

    suspend fun getFavoriteById(favoriteId: Int) = favoriteDAO.getFavoriteById(favoriteId)

}