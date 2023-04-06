package com.itheamc.newsfeedappnchl.data.models

import com.google.gson.annotations.SerializedName

data class SectionResponseEntity(
    @SerializedName("response")
    val response: GuardianResponse<Section>
)