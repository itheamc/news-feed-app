package com.itheamc.newsfeedappnchl.core.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsFeedServices {
    private const val BASE_URL = "https://content.guardianapis.com"

    fun instance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}