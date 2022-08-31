package com.example.historyvideokotlin.model

data class PostTimeline (
    val post_timeline_id  : String,
    val title : String,
    val year : String,
    val content : String,
    val theme_id : String,
    val viewed_count : String,
    val shared_count : String,
    val date_submitted : String,
    val image : String
    )