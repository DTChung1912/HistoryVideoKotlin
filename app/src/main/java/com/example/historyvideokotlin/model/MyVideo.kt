package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyVideo(
    val my_video_id: Int,
    val user_id: String,
    val video_id: Int,
    val title: String,
    val theme_id: Int,
    val creater_image: String,
    val creater: String,
    val platform: String,
    val like_count: Int,
    val view_count: Int,
    val dislike_count: Int,
    val comment_count: Int,
    val download_count: Int,
    val video_url: String,
    val poster_image: String,
    val date_submitted: String,
    val isLike: Int,
    val isLater: Int,
    val isDownload: Int,
    val isView: Int,
    val isShare: Int,
    val isDontCare: Int,
    val view_time: Int,
    val duration: Int
) : Serializable
