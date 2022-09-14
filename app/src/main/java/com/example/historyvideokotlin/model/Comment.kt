package com.example.historyvideokotlin.model

import java.io.Serializable

data class Comment (
    val comment_id : Int,
    val video_id : Int,
    val user_id: String,
    val user_name : String,
    val user_image : String,
    val content : String,
    val date_submitted : String,
    val reply_count : String,
    val like_count : String,
    val dislike_count : String
) : Serializable