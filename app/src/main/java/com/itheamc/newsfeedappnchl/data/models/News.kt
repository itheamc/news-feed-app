package com.itheamc.newsfeedappnchl.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "newses")
data class News(
    @PrimaryKey
    val id: String,
    val type: String,
    val sectionId: String,
    val sectionName: String,
    val webPublicationDate: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val isHosted: Boolean,
    val pillarId: String,
    val pillarName: String,
    val isFavorite: Boolean = false,
)