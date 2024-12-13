package com.example.finalprojectufaz.ui.quiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.quiz.QuizDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(private val quizDao: QuizDao,private val playlistDao: PlaylistDao):ViewModel() {

    private val _quizzes = MutableLiveData<Resource<List<QuizDTO>>>()

    val quizzes : LiveData<Resource<List<QuizDTO>>> get() = _quizzes


    fun fetchQuizzes(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val quizEntityList = quizDao.fetchAll()
                if (quizEntityList.isNotEmpty()){
                    val listModel = mutableListOf<QuizDTO>()
                    quizEntityList.forEach {
                        val pName = playlistDao.getName(it.playlistId)
                        val tCount = quizDao.numberOfQuestions(it.id)
                        if(tCount>2){
                            listModel.add(QuizDTO(it.id,it.playlistId,pName,it.isCompleted,it.wrongAnswers,it.correctAnswers,tCount))
                        }


                    }
                    _quizzes.postValue(Resource.Success(listModel))
                }
            }catch (e:Exception){
                _quizzes.postValue(Resource.Error(e))
            }


        }
    }
}