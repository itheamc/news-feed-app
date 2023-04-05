package com.itheamc.newsfeedappnchl.data.models

data class GuardianResponse<T>(
    val status: String,
    val userTier: String,
    val total: Int,
    val startIndex: Int? = null,
    val pageSize: Int? = null,
    val currentPage: Int? = null,
    val pages: Int? = null,
    val orderBy: String? = null,
    val results: List<T>
)