package com.example.historyvideokotlin.model

data class PostPerson (
    val post_person_id  : String,
    val post_name : String,
    val year : String,
    val content : String,
    val theme_id : String,
    val viewed_count : String,
    val shared_count : String,
    val date_submitted : String,
    val image : String
    )