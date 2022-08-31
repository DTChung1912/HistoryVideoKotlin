package com.example.historyvideokotlin.model

import java.io.Serializable

data class MyPost (
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
    val view_count : String,
    val rate_count : String,
    val date_submitted : String
) : Serializable