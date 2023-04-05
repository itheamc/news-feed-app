package com.itheamc.newsfeedappnchl.data.data_source

import com.itheamc.newsfeedappnchl.data.db.NewsDao
import com.itheamc.newsfeedappnchl.data.db.SectionDao
import com.itheamc.newsfeedappnchl.data.db.UserDao
import com.itheamc.newsfeedappnchl.data.models.ApiResult
import com.itheamc.newsfeedappnchl.data.models.GuardianResponse
import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity

class LocalDataSource(
    private val userDao: UserDao,
    private val newsDao: NewsDao,
    private val sectionDao: SectionDao
) {

    /**
     * Method to fetch the sections data from the local database
     */
    suspend fun fetchSections(): ApiResult<SectionResponseEntity> {
        return try {
            ApiResult.Success(SectionResponseEntity(
                response = GuardianResponse(
                    status = "success",
                    userTier = "developer",
                    total = 10,
                    results = emptyList()
                )
            ))
        } catch (e: Throwable) {
            ApiResult.Error(Exception(cause = e))
        }
    }
}