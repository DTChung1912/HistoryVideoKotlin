package com.example.historyvideokotlin.model

data class PostPlace (
    val post_place_id  : String,
    val post_name : String,
    val place : String,
    val content : String,
    val post_theme_id : String,
    val viewed_count : String,
    val shared_count : String,
    val date_submitted : String,
    val image : String
    )