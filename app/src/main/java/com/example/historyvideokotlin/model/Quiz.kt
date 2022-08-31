package com.example.historyvideokotlin.model

import java.io.Serializable

data class Quiz (
    val quiz_id : String,
    val question : String,
    val theme_id : String,
    val image : String,
    val answer_id : String,
    val correct : String,
    val incorrect_1 : String,
    val incorrect_2 : String,
    val incorrect_3 : String
) : Serializable