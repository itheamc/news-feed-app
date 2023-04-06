package com.itheamc.newsfeedappnchl.core.api

import com.itheamc.newsfeedappnchl.data.models.NewsResponseEntity
import com.itheamc.newsfeedappnchl.data.models.SectionResponseEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsFeedServices {
    @GET("/sections")
    suspend fun fetchSections(
        @Query("q") query: String,
        @Query("format") format: String = "json",
        ): Response<SectionResponseEntity>


    @GET("/search")
    suspend fun fetchNews(
        @Query("q") query: String,
        @Query("section") section: String,
        @Query("reference") reference: String = "",
        @Query("tag") tag: String = "",
        @Query("page") page: Int,
        @Query("page-size") pageSize: Int = 10,
        @Query("order-by") orderBy: String = "newest",
        @Query("format") format: String = "json",
    ): Response<NewsResponseEntity>
}