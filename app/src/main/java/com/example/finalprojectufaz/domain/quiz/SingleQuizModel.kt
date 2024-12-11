package com.example.finalprojectufaz.domain.quiz

data class SingleQuizModel(
    val question: String ,
    val preview:String ,
    val author:String ,
    val trackName:String ,
    val options: List<String>
)
