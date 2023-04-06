package com.itheamc.newsfeedappnchl.data.data_source

import com.itheamc.newsfeedappnchl.data.db.NewsDao
import com.itheamc.newsfeedappnchl.data.db.SectionDao
import com.itheamc.newsfeedappnchl.data.db.UserDao
import com.itheamc.newsfeedappnchl.data.models.*
import com.itheamc.newsfeedappnchl.utils.Amcryption
import com.itheamc.newsfeedappnchl.utils.isValidEmail
import com.itheamc.newsfeedappnchl.utils.isValidUsername
import java.util.UUID

class LocalDataSource(
    private val userDao: UserDao,
    private val newsDao: NewsDao,
    private val sectionDao: SectionDao
) {

    /**
     * ---------------------------------------------------------
     * Methods for User Authentication related functionality
     */

    /*
    Method to register user
     */
    suspend fun registerUser(
        name: String,
        username: String,
        email: String,
        password: String,
        confPassword: String
    ): RegisterResponseEntity {
        return try {

            if (password.trim() != confPassword.trim()) {
                RegisterResponseEntity(
                    user = null,
                    message = "Password doesn't match",
                )
            } else if (!email.isValidEmail()) {
                RegisterResponseEntity(
                    user = null,
                    message = "Invalid email",
                )
            } else if (!username.isValidUsername()) {
                RegisterResponseEntity(
                    user = null,
                    message = "Invalid username",
                )
            }

            // Generating token for user
            val token = UUID.randomUUID().toString()

            // Creating user object
            val user = User(
                name = name,
                username = username,
                email = email,
                password = Amcryption.encrypt(password),
                token = token,
            )

            // Inserting to the database
            val id = userDao.insertUser(user = user)

            // Get user by id
            val createdUser = userDao.getUser(id)

            RegisterResponseEntity(
                user = createdUser,
                message = if (createdUser == null) "Unable to insert user" else null,
            )

        } catch (e: Exception) {
            RegisterResponseEntity(
                user = null,
                message = e.message,
            )
        }
    }

    /*
    Method to login user
     */
    suspend fun loginUser(
        usernameOrEmail: String,
        password: String,
    ): LoginResponseEntity {
        return try {

            if (usernameOrEmail.contains("@")) {

                if (!usernameOrEmail.isValidEmail()) {
                    LoginResponseEntity(
                        user = null,
                        message = "Invalid email",
                    )
                }
            } else {
                if (!usernameOrEmail.isValidUsername()) {
                    LoginResponseEntity(
                        user = null,
                        message = "Invalid username",
                    )
                }
            }

            // Getting user by username or email whichever is provided
            val user = userDao.getUser(usernameOrEmail)

            if (user != null) {

                val decryptedPassword = Amcryption.decrypt(user.password)

                if (decryptedPassword == password) {
                    LoginResponseEntity(
                        user = user,
                        message = null,
                    )
                } else {
                    LoginResponseEntity(
                        user = null,
                        message = "Incorrect password",
                    )
                }
            }

            LoginResponseEntity(
                user = null,
                message = "User not registered with given email or username",
            )

        } catch (e: Exception) {
            LoginResponseEntity(
                user = null,
                message = e.message,
            )
        }
    }

    /**
     * Method to fetch the sections data from the local database
     */
    suspend fun fetchSections(): ApiResult<SectionResponseEntity> {
        return try {
            ApiResult.Success(
                SectionResponseEntity(
                    response = GuardianResponse(
                        status = "success",
                        userTier = "developer",
                        total = 10,
                        results = emptyList()
                    )
                )
            )
        } catch (e: Throwable) {
            ApiResult.Error(Exception(cause = e))
        }
    }
}