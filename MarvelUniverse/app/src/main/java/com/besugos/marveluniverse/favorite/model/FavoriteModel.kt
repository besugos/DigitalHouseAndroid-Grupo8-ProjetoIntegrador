package com.besugos.marveluniverse.favorite.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_table")
data class FavoriteModel (
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "path")
    val path: String?,
    @ColumnInfo(name = "extension")
    val extension: String?
)