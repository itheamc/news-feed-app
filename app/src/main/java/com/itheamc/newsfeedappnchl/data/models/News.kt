package com.itheamc.newsfeedappnchl.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News(
    @PrimaryKey
    val id: String,
)