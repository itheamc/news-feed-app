package com.itheamc.newsfeedappnchl.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(defaultValue = "") val name: String,
    @ColumnInfo(defaultValue = "") val email: String,
    @ColumnInfo(defaultValue = "") val username: String,
    @ColumnInfo(defaultValue = "") val password: String,
    @ColumnInfo(defaultValue = "") val token: String,
)
