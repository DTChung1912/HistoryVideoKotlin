package com.example.historyvideokotlin.model

data class AnswerStatus(
    val content: String? = "",
    var status: Int? = AnswerType.NOT.ordinal
)
