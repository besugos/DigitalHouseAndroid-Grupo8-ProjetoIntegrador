package com.besugos.marveluniverse.favorite.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "favorite_table")
data class FavoriteModel(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    val id: Int,
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "path")
    val pathImage: String?,
    @ColumnInfo(name = "events")
    val events: String?,
    @ColumnInfo(name = "comics")
    val comics: String?
) : Parcelable