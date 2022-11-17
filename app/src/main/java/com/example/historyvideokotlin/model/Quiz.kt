package com.example.historyvideokotlin.model

import java.io.Serializable

data class Quiz(
    val quiz_id: Int = 0,
    val question: String = "",
    val theme_id: Int = 0,
    val image: String = "",
    val answer_id: Int = 0,
    val correct: String = "",
    val incorrect_1: String = "",
    val incorrect_2: String = "",
    val incorrect_3: String = "",
    val date_submitted: String = ""
) : Serializable
