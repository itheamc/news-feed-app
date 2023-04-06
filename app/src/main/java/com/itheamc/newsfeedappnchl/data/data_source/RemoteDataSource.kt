package com.itheamc.newsfeedappnchl.data.data_source

import com.itheamc.newsfeedappnchl.core.api.NewsFeedServices
import com.itheamc.newsfeedappnchl.data.models.ApiResult
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
                    val sectionsResponseEntity = response.body();

                    if (sectionsResponseEntity != null) {
                        // Emit success state with data
                        emit(
                            ApiResult.Success(
                                sectionsResponseEntity
                            )
                        )
                    }
                }

                // Emit success state with data
                emit(
                    ApiResult.Error(Exception(message = response.message()))
                )


            } catch (e: Throwable) {
                // Emit error state
                emit(ApiResult.Error(Exception(e)))
            }
        }


    /**
     * ---------------------------------------------------------
     * Methods for Sections related data handling
     */

    /*
      Method to fetch the sections data from the local database
     */
    suspend fun fetchNews(

    ): Flow<ApiResult<SectionResponseEntity>> =
        flow {
            try {
                // Emit loading state
                emit(ApiResult.Loading)

                // Fetch sections and count from database
                val response = newsFeedServices.fetchSections(query = "query")

                if (response.isSuccessful) {
                    val sectionsResponseEntity = response.body();

                    if (sectionsResponseEntity != null) {
                        // Emit success state with data
                        emit(
                            ApiResult.Success(
                                sectionsResponseEntity
                            )
                        )
                    }
                }

                // Emit success state with data
                emit(
                    ApiResult.Error(Exception(message = response.message()))
                )


            } catch (e: Throwable) {
                // Emit error state
                emit(ApiResult.Error(Exception(e)))
            }
        }

}