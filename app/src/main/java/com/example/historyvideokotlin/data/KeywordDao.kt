package com.example.historyvideokotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.historyvideokotlin.model.Keyword

@Dao
interface KeywordDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertKeyword(keyword: Keyword)

    @Update
    suspend fun updateKeyword(keyword: Keyword)

    @Delete
    fun deleteKeyword(keyword: Keyword)

    @Query("DELETE FROM keyword_table")
    suspend fun deleteAllKeywords()

    @Query("SELECT * FROM keyword_table WHERE search_type = 1 ORDER BY keyword_id ASC")
    fun getVideoKeyword(): LiveData<List<Keyword>>

    @Query("SELECT * FROM keyword_table WHERE search_type = 0 ORDER BY keyword_id ASC")
    fun getPostKeyword(): LiveData<List<Keyword>>
}