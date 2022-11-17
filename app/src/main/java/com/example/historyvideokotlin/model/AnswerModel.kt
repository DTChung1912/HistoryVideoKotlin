package com.example.historyvideokotlin.model

data class AnswerModel(
    var ansA: AnswerStatus? = AnswerStatus(),
    var ansB: AnswerStatus? = AnswerStatus(),
    var ansC: AnswerStatus? = AnswerStatus(),
    var ansD: AnswerStatus? = AnswerStatus()
)
