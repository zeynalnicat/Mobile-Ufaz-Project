package com.example.finalprojectufaz.ui.quiz.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.ui.quiz.viewmodel.QuizViewModel

class QuizFactory(private val quizDao: QuizDao,private val playlistDao: PlaylistDao):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return QuizViewModel(quizDao,playlistDao) as T
    }
}