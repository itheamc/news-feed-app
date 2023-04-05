package com.itheamc.newsfeedappnchl.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey
    val id: String,
    val webTitle: String,
    val webUrl: String,
    val apiUrl: String,
    val code: String? = null,
    val editions: List<Section>
)
