package com.example.finalprojectufaz.domain.quiz

data class QuizDTO(
    val id:Int,
    val name: String ,
    val isCompleted: Boolean? = false,
    val wrongAnswers: Int? = null,
    val correctAnswers: Int? = null,
    val numberOfQuestions: Int? =null
)
