package com.itheamc.newsfeedappnchl.data.repositories

import com.itheamc.newsfeedappnchl.data.data_source.LocalDataSource
import com.itheamc.newsfeedappnchl.data.data_source.RemoteDataSource
import com.itheamc.newsfeedappnchl.data.models.ApiResult
import com.itheamc.newsfeedappnchl.data.models.NewsResponseEntity
import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity
import kotlinx.coroutines.flow.Flow

class NewsFeedRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) {

    /**
     * Method to fetch the sections data
     */
    suspend fun fetchSections(query: String = "news"): Flow<ApiResult<SectionResponseEntity>> {
        return remoteDataSource.fetchSections(query = query)
    }

    /**
     * Method to fetch the news data
     */
    suspend fun fetchNews(
        section: String,
        query: String = "",
        reference: String = "",
        tag: String = "",
        limit: Int = 10,
        page: Int = 1,
    ): Flow<ApiResult<NewsResponseEntity>> {
        return remoteDataSource.fetchNews(
            section = section,
            query = query,
            reference = reference,
            tag = tag,
            limit = limit,
            page = page,
        )
    }

}