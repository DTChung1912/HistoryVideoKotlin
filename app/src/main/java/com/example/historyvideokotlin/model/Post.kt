package com.example.historyvideokotlin.model

import java.io.Serializable

data class Post (
    val post_id  : String,
    val post_type_id : String,
    val post_type_name : String,
    val theme_id : String,
    val title : String,
    val content : String,
    val image : String,
    val description : String,
    val timeline : String,
    val place : String,
    val read_count : String,
    val download_count : String,
    val rate_count : String,
    val date_submitted : String
) : Serializable