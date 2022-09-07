package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyPost(
    val my_post_id: Int,
    val user_id: String,
    val post_id: Int,
    val post_type_id: Int,
    val post_type_name: String,
    val theme_id: Int,
    val title: String,
    val content: String,
    val image: String,
    val description: String,
    val timeline: String,
    val place: String,
    val read_count: Int,
    val download_count: Int,
    val rate_count: Int,
    val date_submitted: String,
    val isRead: Int,
    val isDownload: Int,
    val rate: Int
) : Serializable