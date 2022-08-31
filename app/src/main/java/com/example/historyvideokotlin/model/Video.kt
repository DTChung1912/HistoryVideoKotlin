package com.example.historyvideokotlin.model

import java.io.Serializable

data class Video(
    val video_id: String,
    val title: String,
    val theme_id: String,
    val creater_image: String,
    val creater: String,
    val platform: String,
    val like_count: Int,
    val view_count: Int,
    val dislike_count: Int,
    val comment_count: Int,
    val share_count: Int,
    val video_url: String,
    val poster_image: String,
    val date_submitted: String
) : Serializable