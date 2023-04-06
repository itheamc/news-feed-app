package com.itheamc.newsfeedappnchl.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "newses")
data class News(
    @SerializedName("_id") @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @SerializedName("id") @ColumnInfo(defaultValue = "") var newsId: String,
    @ColumnInfo(defaultValue = "") var type: String,
    @ColumnInfo(defaultValue = "") var sectionId: String,
    @ColumnInfo(defaultValue = "") var sectionName: String,
    @ColumnInfo(defaultValue = "") var webPublicationDate: String,
    @ColumnInfo(defaultValue = "") var webTitle: String,
    @ColumnInfo(defaultValue = "") var webUrl: String,
    @ColumnInfo(defaultValue = "") var apiUrl: String,
    var isHosted: Boolean = false,
    @ColumnInfo(defaultValue = "") var pillarId: String,
    @ColumnInfo(defaultValue = "") var pillarName: String,
    var isFavorite: Boolean = false,
) {
    constructor() : this(0, "", "", "", "", "", "", "", "", false, "", "", false)

}