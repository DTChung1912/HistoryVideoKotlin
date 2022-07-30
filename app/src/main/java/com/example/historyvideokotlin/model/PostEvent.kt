package com.example.historyvideokotlin.model

data class PostEvent (
    val post_event_id  : String,
    val title : String,
    val year : String,
    val content : String,
    val viewed_count : String,
    val shared_count : String,
    val post_theme_id : String,
    val date_submitted : String,
    val image :String
    )