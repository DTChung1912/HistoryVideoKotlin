package com.example.historyvideokotlin.model

data class ReplyRequest(
    val reply_id: Int?,
    val comment_id: Int,
    val user_id: String,
    val content: String,
    val date_submitted: String,
    val like_count: Int = 0,
    val dislike_count: Int = 0
)
