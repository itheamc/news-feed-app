package com.itheamc.newsfeedappnchl

import android.app.Application
import com.itheamc.newsfeedappnchl.core.api.NewsFeedRetrofitHelper
import com.itheamc.newsfeedappnchl.core.api.NewsFeedServices
import com.itheamc.newsfeedappnchl.data.data_source.LocalDataSource
import com.itheamc.newsfeedappnchl.data.data_source.RemoteDataSource
import com.itheamc.newsfeedappnchl.data.db.NewsFeedDatabase
import com.itheamc.newsfeedappnchl.data.repositories.NewsFeedRepository

class NCHLApplication : Application() {
    lateinit var newsFeedRepository: NewsFeedRepository

    override fun onCreate() {
        super.onCreate()

        // Database initialization
        val database: NewsFeedDatabase by lazy {
            NewsFeedDatabase.instance(this)
        }

        // Retrofit service initialization
        val newsFeedServices: NewsFeedServices by lazy {
            NewsFeedRetrofitHelper.instance().create(NewsFeedServices::class.java)
        }

        newsFeedRepository = NewsFeedRepository(
            localDataSource = LocalDataSource(
                userDao = database.userDao(),
                newsDao = database.newsDao(),
                sectionDao = database.sectionDao()
            ),
            remoteDataSource = RemoteDataSource(
                newsFeedServices = newsFeedServices
            )
        )
    }
}