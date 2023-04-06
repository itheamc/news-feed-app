package com.itheamc.newsfeedappnchl.data.db

import androidx.room.*
import com.itheamc.newsfeedappnchl.data.models.Section

@Dao
interface SectionDao {
    /**
     * To get all the sections
     * [limit] -> size of sections to be fetched
     * [offset] -> offset is the point after which rows not more than limit is fetched
     */
    @Query("SELECT * FROM sections ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun sections(limit: Int, offset: Int): List<Section>

    /**
     * Function to insert [section] to our database
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSection(section: Section): String

    /**
     * Function to delete [section] from our database
     */
    @Delete
    suspend fun deleteSection(section: Section)

    /**
     * Function to update [section] from our database
     */
    @Update
    suspend fun updateSection(section: Section)

    /**
     * Function to count the sections in our database
     */
    @Query("SELECT COUNT(*) FROM sections")
    suspend fun count(): Long

    /**
     * Function to get section by id
     */
    @Query("SELECT * FROM sections WHERE id =:id")
    suspend fun getSection(id: String): Section?
}