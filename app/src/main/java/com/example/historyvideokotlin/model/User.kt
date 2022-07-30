package com.example.historyvideokotlin.model

data class User (
    val user_id: Int,

    val user_name: String,

    val user_image: String,

    val email: String,

    val phone_number: String,

    val last_active: String,

    val liked_video_id : String,

    val viewed_video_id : String,

    val account_type_id : String
)