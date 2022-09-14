package com.example.historyvideokotlin.model

import java.io.Serializable

data class Reply (
    val reply_id : Int,
    val comment_id : Int,
    val user_id: String,
    val user_name : String,
    val user_image : String,
    val partner_name : String,
    val content : String,
    val date_submitted : String,
    val like_count : String,
    val dislike_count : String
) : Serializable