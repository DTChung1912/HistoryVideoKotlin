package com.example.historyvideokotlin.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class QuizListData(
    @SerializedName("quizList")
    var quizList: List<Quiz>
) : Serializable
