package com.besugos.marveluniverse.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.besugos.marveluniverse.favorite.model.FavoriteModel
import com.besugos.marveluniverse.favorite.room.FavoriteDAO

@Database(entities = [FavoriteModel::class], version = 4)
abstract class MyDataBase: RoomDatabase() {

    abstract fun favoriteDAO(): FavoriteDAO

    companion object {

        @Volatile
        private var INSTANCE: MyDataBase? = null

        fun getDataBaseClient(context: Context): MyDataBase {

            if(INSTANCE != null) return INSTANCE!!

            synchronized(this) {
                INSTANCE = Room
                    .databaseBuilder(context, MyDataBase::class.java, "MARVEL_DB")
                    .fallbackToDestructiveMigration()
                    .build()
                return INSTANCE!!
            }

        }

    }

}