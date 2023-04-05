package com.itheamc.newsfeedappnchl.data.db

import androidx.room.*
import com.itheamc.newsfeedappnchl.data.models.User

@Dao
interface UserDao {

    /**
     * Function to insert [user] to our database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User): Long

    /**
     * Function to delete [user] from our database
     */
    @Delete
    suspend fun deleteUser(user: User)

    /**
     * Function to update [user] from our database
     */
    @Update
    suspend fun updateUser(user: User)

}