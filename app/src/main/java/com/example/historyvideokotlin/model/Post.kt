package com.example.historyvideokotlin.model

data class Post (
    val post_id : String,
    val title : String,
    val content : String,
    val timeline : String,
    val image : String,
    val post_theme_id : String,
    val view_count : String,
    val share_count : String
    )