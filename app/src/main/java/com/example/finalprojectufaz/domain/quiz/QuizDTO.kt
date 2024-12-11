package com.example.finalprojectufaz.domain.quiz

data class QuizDTO(
    val id:Int,
    val playlistId : Int ,
    val playlistName: String ,
    val isCompleted: Boolean? = false,
    val wrongAnswers: Int? = null,
    val correctAnswers: Int? = null,
    val quizzes : List<SingleQuizModel>? = null
)
