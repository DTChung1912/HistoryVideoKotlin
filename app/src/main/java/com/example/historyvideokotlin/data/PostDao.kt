package com.example.historyvideokotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.historyvideokotlin.model.DownloadPost

@Dao
interface PostDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPost(post: DownloadPost)

    @Update
    suspend fun updatePost(post: DownloadPost)

    @Delete
    fun deletePost(post: DownloadPost)

    @Query("DELETE FROM post_table")
    suspend fun deleteAllPosts()

    @Query("SELECT * FROM post_table ORDER BY post_id ASC")
    fun readAllData(): LiveData<List<DownloadPost>>
}