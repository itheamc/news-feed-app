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

    /**
     * Function to get user by id
     */
    @Query("SELECT * FROM users WHERE id =:id")
    suspend fun getUser(id: Long): User?

    /**
     * Function to get user by username or email
     */
    @Query("SELECT * FROM users WHERE username =:usernameOrEmail OR email =:usernameOrEmail")
    suspend fun getUser(usernameOrEmail: String): User?

}