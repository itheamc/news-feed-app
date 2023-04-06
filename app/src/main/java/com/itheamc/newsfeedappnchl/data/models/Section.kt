package com.itheamc.newsfeedappnchl.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "sections")
data class Section(
    @PrimaryKey(autoGenerate = true) var id: Long = 0,
    @SerializedName("id") @ColumnInfo(defaultValue = "") var sectionId: String,
    @ColumnInfo(defaultValue = "") var webTitle: String,
    @ColumnInfo(defaultValue = "") var webUrl: String,
    @ColumnInfo(defaultValue = "") var apiUrl: String,
    @ColumnInfo(defaultValue = "") var code: String = "",
    @Ignore var editions: List<Section>? = null
) {
    constructor() : this(0, "", "", "", "", "", null)
}
