package com.globallogic.homelist.local

import android.content.Context
import androidx.annotation.NonNull
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.globallogic.homelist.local.database.HomeListDAO
import com.globallogic.homelist.local.model.HomeListLocalData

@Database(
    entities = [HomeListLocalData::class],
    version = 1,
    exportSchema = false
)
abstract class HomeListDB : RoomDatabase() {
    companion object {
        private val LOCK = Any()
        private const val DATABASE_NAME = "laptopslist.db"

        @Volatile
        private var INSTANCE: HomeListDB? = null

        fun getInstance(@NonNull context: Context): HomeListDB {
            if (INSTANCE == null) {
                synchronized(LOCK) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(
                            context,
                            HomeListDB::class.java,
                            DATABASE_NAME
                        ).build()
                    }
                }
            }
            return INSTANCE!!
        }
    }

    abstract fun getHomeListDAO(): HomeListDAO
}