package com.itheamc.newsfeedappnchl.data.data_source

import com.itheamc.newsfeedappnchl.core.api.NewsFeedServices
import com.itheamc.newsfeedappnchl.data.models.ApiResult
import com.itheamc.newsfeedappnchl.data.models.NewsResponseEntity
import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteDataSource(
    private val newsFeedServices: NewsFeedServices
) {

    /**
     * ---------------------------------------------------------
     * Methods for Sections related data handling
     */

    /*
      Method to fetch the sections data from the local database
     */
    suspend fun fetchSections(query: String = "news"): Flow<ApiResult<SectionResponseEntity>> =
        flow {
            try {
                // Emit loading state
                emit(ApiResult.Loading)

                // Fetch sections and count from database
                val response = newsFeedServices.fetchSections(query = query)

                if (response.isSuccessful) {
                    val sectionsResponseEntity = response.body()

                    if (sectionsResponseEntity != null) {
                        // Emit success state with data
                        emit(
                            ApiResult.Success(
                                sectionsResponseEntity
                            )
                        )
                        return@flow
                    }
                }

                // Emit success state with data
                emit(
                    ApiResult.Error(Exception(response.message()))
                )


            } catch (e: Throwable) {
                // Emit error state
                emit(ApiResult.Error(Exception(e)))
            }
        }


    /**
     * ---------------------------------------------------------
     * Methods for News related data handling
     */

    /*
      Method to fetch the news data from the local database
     */
    suspend fun fetchNews(
        section: String,
        query: String = "",
        reference: String = "",
        tag: String = "",
        limit: Int = 10,
        page: Int = 1,
    ): Flow<ApiResult<NewsResponseEntity>> =
        flow {
            try {
                // Emit loading state
                emit(ApiResult.Loading)

                // Fetch news and count from database
                val response = newsFeedServices.fetchNews(
                    query = query,
                    section = section,
                    reference = reference,
                    tag = tag,
                    page = page,
                    pageSize = limit
                )

                if (response.isSuccessful) {
                    val newsResponseEntity = response.body()

                    if (newsResponseEntity != null) {
                        // Emit success state with data
                        emit(
                            ApiResult.Success(
                                newsResponseEntity
                            )
                        )
                    }
                }

                // Emit success state with data
                emit(
                    ApiResult.Error(Exception(response.message()))
                )


            } catch (e: Throwable) {
                // Emit error state
                emit(ApiResult.Error(Exception(e)))
            }
        }

}