package com.example.historyvideokotlin.model

data class SelectAnswer(
    val number: Int? = 0,
    val title: String? = "",
    val content: String? = "",
    val isCorrect: Boolean? = false
)