package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyPostStatus(
    val my_post_id: Int,
    val user_id: String,
    val post_id: Int,
    val isRead: Int,
    val isDownload: Int,
    val rate: Int
) : Serializable
