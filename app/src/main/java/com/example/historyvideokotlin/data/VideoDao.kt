package com.example.historyvideokotlin.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.historyvideokotlin.model.DownloadVideo
import com.example.historyvideokotlin.model.Video
import retrofit2.http.DELETE

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertVideo(video: DownloadVideo)

    @Update
    suspend fun updateVideo(video: DownloadVideo)

    @Delete
    fun deleteVideo(video: DownloadVideo)

    @Query("DELETE FROM video_table")
    suspend fun deleteAllVideos()

    @Query("SELECT * FROM video_table ORDER BY video_id ASC")
    fun readAllData(): LiveData<List<DownloadVideo>>
}