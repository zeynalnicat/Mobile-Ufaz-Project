package com.example.finalprojectufaz.domain.quiz

data class QuizQuestionsModel(
    val options: List<List<String>>,
    val answers : List<String>,
    val previews: List<String>,
    val questions:List<String>,
    val timer : Int ,

){
    companion object {
        var wrongAnswers: Int = 0
        var correctAnswers: Int = 0
    }

}
