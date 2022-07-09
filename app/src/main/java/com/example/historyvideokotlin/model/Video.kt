package com.example.historyvideokotlin.model

data class Video (
    val video_id : String,
    val title : String,
    val video_theme_id : String,
    val image : String,
    val creater : String,
    val platform : String,
    val emoji_count : Int,
    val view_count : Int,
    val comment_count : Int,
    val share_count : Int,
    val video_url : String
)