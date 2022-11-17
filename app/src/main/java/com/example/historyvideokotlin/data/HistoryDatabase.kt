package com.example.historyvideokotlin.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.historyvideokotlin.model.DownloadPost
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.Keyword

@Database(
    entities = [DownloadVideo::class, DownloadPost::class, Keyword::class],
    version = 1,
    exportSchema = true
)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun videoDao(): VideoDao
    abstract fun postDao(): PostDao
    abstract fun keywordDao(): KeywordDao

    companion object {
        @Volatile
        private var INSTANCE: HistoryDatabase? = null

        fun getDatabase(context: Context): HistoryDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    HistoryDatabase::class.java,
                    "download_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}