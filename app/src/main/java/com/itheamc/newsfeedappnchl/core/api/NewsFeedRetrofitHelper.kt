package com.itheamc.newsfeedappnchl.core.api

import retrofit2.Response
import retrofit2.http.GET

interface NewsFeedRetrofitHelper {
    @GET("/sections")
    suspend fun getEntries(): Response<List<String>>
}