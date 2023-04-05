package com.itheamc.newsfeedappnchl.data.repositories

import com.itheamc.newsfeedappnchl.data.data_source.LocalDataSource
import com.itheamc.newsfeedappnchl.data.data_source.RemoteDataSource

class NewsFeedRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {


}