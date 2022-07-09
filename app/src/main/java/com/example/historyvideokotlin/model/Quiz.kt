package com.example.historyvideokotlin.model

data class Quiz (
    val quiz_id : String,
    val question : String,
    val quiz_theme_id : String,
    val image : String,
    val correct_answer : String,
    val incorrect_answer_id : String
)