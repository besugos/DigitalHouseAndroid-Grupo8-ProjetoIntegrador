package com.besugos.marveluniverse.favorite.room

import androidx.room.*
import com.besugos.marveluniverse.favorite.model.FavoriteModel

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteModel)

    @Delete
    suspend fun removeFavorite(favorite: FavoriteModel)

    @Query("SELECT * FROM favorite_table ORDER BY name")
    suspend fun getFavorites(): List<FavoriteModel>

    @Query("SELECT * FROM favorite_table WHERE name LIKE :nameStartsWith ORDER BY name")
    suspend fun getFavoritesByName(nameStartsWith: String? = ""): List<FavoriteModel>

    @Query("SELECT * FROM favorite_table WHERE id = :favoriteId")
    suspend fun getFavoriteById(favoriteId: Int): FavoriteModel

}