package com.itheamc.newsfeedappnchl.data.data_source

import com.itheamc.newsfeedappnchl.data.db.NewsDao
import com.itheamc.newsfeedappnchl.data.db.SectionDao
import com.itheamc.newsfeedappnchl.data.db.UserDao
import com.itheamc.newsfeedappnchl.data.models.*
import com.itheamc.newsfeedappnchl.utils.Amcryption
import com.itheamc.newsfeedappnchl.utils.isValidEmail
import com.itheamc.newsfeedappnchl.utils.isValidUsername
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
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
                id = 0,
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

    /*
    Method to get user by id
     */
    suspend fun getUser(
        id: Long,
        token: String,
    ): User? {
        return try {
            val user = userDao.getUser(id = id)

            return if (user != null && user.token == token) user else null
        } catch (e: Exception) {
            null
        }
    }

    /**
     * ---------------------------------------------------------
     * Methods for Sections related data handling
     */

    /*
      Method to fetch the sections data from the local database
     */
    suspend fun fetchSections(
        limit: Int = 10,
        offset: Int = 0
    ): Flow<ApiResult<SectionResponseEntity>> = flow {
        try {
            // Emit loading state
            emit(ApiResult.Loading)

            // Fetch sections and count from database
            val sections = sectionDao.sections(limit, offset)
            val count = sectionDao.count()

            // Emit success state with data
            emit(
                ApiResult.Success(
                    SectionResponseEntity(
                        response = GuardianResponse(
                            status = "success",
                            userTier = "developer",
                            total = count.toInt(),
                            results = sections
                        )
                    )
                )
            )
        } catch (e: Throwable) {
            // Emit error state
            emit(ApiResult.Error(Exception(e)))
        }
    }


    /*
      Method to add the section list data to the local database with sectionDao
     */
    suspend fun insertSections(
        list: List<Section>
    ): List<String> {
        return try {
            // Insert sections into database
            sectionDao.insertAll(list)

            // Return list of inserted section IDs
            list.map { it.sectionId }
        } catch (e: Throwable) {
            // Handle error
            emptyList()
        }
    }

    /*
      Method to add the section data to the local database with sectionDao
     */
    suspend fun insertSection(section: Section): String? {
        return try {
            // Insert section into database
            sectionDao.insertSection(section)
            section.sectionId
        } catch (e: Throwable) {
            // Handle error
            null
        }
    }

    /*
      Method to update the section data to the local database with sectionDao
     */
    suspend fun updateSection(section: Section): Boolean {
        return try {
            // Update section in database
            sectionDao.updateSection(section)
            true
        } catch (e: Throwable) {
            // Handle error
            false
        }
    }


    /*
      Method to delete the section data to the local database with sectionDao
     */
    suspend fun deleteSection(section: Section): Boolean {
        return try {
            // Delete section in database
            sectionDao.deleteSection(section)
            true
        } catch (e: Throwable) {
            // Handle error
            false
        }
    }

    /**
     * ---------------------------------------------------------
     * Methods for News related data handling
     */

    /*
      Method to fetch the news data from the local database
     */
    suspend fun fetchNews(
        limit: Int = 10,
        offset: Int = 0
    ): Flow<ApiResult<NewsResponseEntity>> = flow {
        try {
            // Emit loading state
            emit(ApiResult.Loading)

            // Fetch news and count from database
            val news = newsDao.newses(limit, offset)
            val count = newsDao.count()

            // Emit success state with data
            emit(
                ApiResult.Success(
                    NewsResponseEntity(
                        response = GuardianResponse(
                            status = "success",
                            userTier = "developer",
                            total = count.toInt(),
                            startIndex = offset,
                            pageSize = limit,
                            currentPage = (offset / limit) + 1,
                            results = news
                        )
                    )
                )
            )
        } catch (e: Throwable) {
            // Emit error state
            emit(ApiResult.Error(Exception(e)))
        }
    }


    /*
      Method to add the news list data to the local database with newsDao
     */
    suspend fun insertNews(
        list: List<News>
    ): List<String> {
        return try {
            // Insert news into database
            newsDao.insertAll(list)

            // Return list of inserted news IDs
            list.map { it.newsId }
        } catch (e: Throwable) {
            // Handle error
            emptyList()
        }
    }

    /*
      Method to add the news data to the local database with newsDao
     */
    suspend fun insertNews(news: News): String? {
        return try {
            // Insert news into database
            newsDao.insertNews(news)

            news.newsId
        } catch (e: Throwable) {
            // Handle error
            null
        }
    }

    /*
      Method to update the news data in the local database with newsDao
     */
    suspend fun updateNews(news: News): Boolean {
        return try {
            // Update news in database
            newsDao.updateNews(news)
            true
        } catch (e: Throwable) {
            // Handle error
            false
        }
    }


    /*
      Method to delete the news data from the local database with newsDao
     */
    suspend fun deleteNews(news: News): Boolean {
        return try {
            // Delete news from database
            newsDao.deleteNews(news)
            true
        } catch (e: Throwable) {
            // Handle error
            false
        }
    }
}