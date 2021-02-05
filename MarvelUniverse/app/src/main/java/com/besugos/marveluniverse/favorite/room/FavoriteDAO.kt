package com.besugos.marveluniverse.favorite.room

import androidx.room.*
import com.besugos.marveluniverse.favorite.model.FavoriteModel

@Dao
interface FavoriteDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoriteModel)

    @Query("DELETE FROM favorite_table WHERE id = :favoriteId")
    suspend fun removeFavorite(favoriteId: Int)

    @Query("SELECT * FROM favorite_table WHERE user_id = :userId ORDER BY name")
    suspend fun getFavorites(userId: String): List<FavoriteModel>

    @Query("SELECT * FROM favorite_table WHERE user_id = :userId AND name LIKE :nameStartsWith ORDER BY name")
    suspend fun getFavoritesByName(userId: String, nameStartsWith: String? = ""): List<FavoriteModel>

    @Query("SELECT * FROM favorite_table WHERE id = :favoriteId")
    suspend fun getFavoriteById(favoriteId: Int): FavoriteModel

}