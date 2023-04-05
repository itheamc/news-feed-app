package com.itheamc.newsfeedappnchl.data.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.itheamc.newsfeedappnchl.NCHLApplication
import com.itheamc.newsfeedappnchl.data.models.Section


@Database(entities = [Section::class], version = 1, exportSchema = false)
abstract class NewsFeedDatabase: RoomDatabase() {

    /**
     * Abstract function to return the instance of the UserDao
     */
    abstract fun userDao(): UserDao

    /**
     * Abstract function to return the instance of the SectionDao
     */
    abstract fun sectionDao(): SectionDao

    /**
     * Abstract function to return the instance of the NewsDao
     */
    abstract fun newsDao(): NewsDao

    /**
     * Static variable and function
     */
    companion object {

        @Volatile
        private var INSTANCE: NewsFeedDatabase? = null

        /**
         * Singleton function to return the instance of the database
         */
        fun instance(application: NCHLApplication): NewsFeedDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    application.applicationContext,
                    NewsFeedDatabase::class.java,
                    "nchl_news_app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}