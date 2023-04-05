package com.itheamc.newsfeedappnchl.core.api

import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedServices {
    @GET("/sections")
    suspend fun fetchSections(
        @Query("q") query: String,
        @Query("format") format: String = "json"
    ): Response<SectionResponseEntity>
}