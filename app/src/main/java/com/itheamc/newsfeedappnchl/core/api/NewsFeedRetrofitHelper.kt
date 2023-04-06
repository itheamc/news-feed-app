package com.itheamc.newsfeedappnchl.core.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NewsFeedRetrofitHelper {

    private const val BASE_URL = "https://content.guardianapis.com"

    fun instance(): Retrofit {
        val client = OkHttpClient.Builder().addInterceptor { chain ->
            val req = chain.request().newBuilder()
                .url(
                    chain.request().url.newBuilder()
                        .addQueryParameter("api-key", "d60dc606-7769-4bbd-a88a-e711bdb9e9ee")
                        .build()
                )
                .build()
            chain.proceed(req)
        }.build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}