package com.itheamc.newsfeedappnchl.data.db

import androidx.room.*
import com.itheamc.newsfeedappnchl.data.models.News

@Dao
interface NewsDao {
    /**
     * To get all the news
     * [limit] -> size of sections to be fetched
     * [offset] -> offset is the point after which rows not more than limit is fetched
     */
    @Query("SELECT * FROM news ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun newses(limit: Int, offset: Int): List<News>

    /**
     * Function to insert [news] to our database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNews(news: News): String

    /**
     * Function to delete [news] from our database
     */
    @Delete
    suspend fun deleteNews(news: News)

    /**
     * Function to update [news] from our database
     */
    @Update
    suspend fun updateNews(news: News)

    /**
     * Function to count the news in our database
     */
    @Query("SELECT COUNT(*) FROM news")
    suspend fun count(): Long

    /**
     * Function to get news by id
     */
    @Query("SELECT * FROM news WHERE id =:id")
    suspend fun getNews(id: String): News?
}