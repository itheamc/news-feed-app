package com.itheamc.newsfeedappnchl.data.models

import com.google.gson.annotations.SerializedName

data class NewsResponseEntity(
    @SerializedName("response")
    val response: GuardianResponse<News>
)
