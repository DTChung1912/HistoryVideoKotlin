package com.example.historyvideokotlin.model

import java.io.Serializable

data class ResultCount(
    var notCount: Int,
    var correctCount: Int,
    var wrongCount: Int
) : Serializable