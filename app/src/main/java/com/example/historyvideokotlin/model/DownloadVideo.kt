package com.example.historyvideokotlin.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "video_table")
data class DownloadVideo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "video_id")
    var video_id: Int? = null,
    @ColumnInfo(name = "title")
    var title: String? = null,
    @ColumnInfo(name = "theme_id")
    var theme_id: Int? = null,
    @ColumnInfo(name = "creater_image")
    var creater_image: String? = null,
    @ColumnInfo(name = "creater")
    var creater: String? = null,
    @ColumnInfo(name = "platform")
    var platform: String? = null,
    @ColumnInfo(name = "like_count")
    var like_count: Int? = null,
    @ColumnInfo(name = "view_count")
    var view_count: Int? = null,
    @ColumnInfo(name = "dislike_count")
    var dislike_count: Int? = null,
    @ColumnInfo(name = "comment_count")
    var comment_count: Int? = null,
    @ColumnInfo(name = "download_count")
    var download_count: Int? = null,
    @ColumnInfo(name = "video_url")
    var video_url: String? = null,
    @ColumnInfo(name = "poster_image")
    var poster_image: String? = null,
    @ColumnInfo(name = "duration")
    var duration: Int? = null,
    @ColumnInfo(name = "date_submitted")
    var date_submitted: String? = null
) : Parcelable