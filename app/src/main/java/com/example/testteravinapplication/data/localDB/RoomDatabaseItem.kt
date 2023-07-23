package com.example.testteravinapplication.data.localDB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [MovieLocal::class],
    version = 5, exportSchema = true)
abstract class RoomDatabaseItem: RoomDatabase() {

    abstract fun dao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE: RoomDatabaseItem? = null

        fun getDatabase(context: Context): RoomDatabaseItem {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RoomDatabaseItem::class.java,
                    "database"
                )
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}