package com.example.finalprojectufaz.ui.single_quiz.factory


import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.ui.single_quiz.viewmodel.SingleQuizViewModel

class SingleQuizFactory(private val quizDao: QuizDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SingleQuizViewModel(quizDao) as T
    }
}