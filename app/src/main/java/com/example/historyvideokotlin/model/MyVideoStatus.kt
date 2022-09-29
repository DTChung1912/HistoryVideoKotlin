package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyVideoStatus(
    val my_video_id: Int,
    val user_id: String,
    val video_id: Int,
    val isLike: Int,
    val isLater: Int,
    val isDownload: Int,
    val isView: Int,
    val isShare: Int,
    val isDontCare: Int,
    val view_time: Int,
    val duration: Int
) : Serializable
