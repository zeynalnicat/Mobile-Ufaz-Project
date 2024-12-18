package com.example.finalprojectufaz.ui.single_quiz.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.domain.quiz.QuizQuestionsModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SingleQuizViewModel(private val quizDao: QuizDao):ViewModel() {

    private val _questions = MutableLiveData<QuizQuestionsModel>()

    val questions : LiveData<QuizQuestionsModel> get() = _questions


    fun fetchQuizQuestions(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val questionsEntity = quizDao.fetchQuizQuestions(id)
                val duration = quizDao.getDuration(id)
                val previews = List(questionsEntity.size){index->
                    questionsEntity[index].preview
                }
                val questions = List(questionsEntity.size){index->
                    questionsEntity[index].question
                }
                val answers = List(questionsEntity.size){index->
                    questionsEntity[index].answer
                }
                val options = List(questionsEntity.size) { index ->
                    val allAnswers = answers.toMutableList()
                    val correctAnswer = answers[index]
                    allAnswers.remove(correctAnswer)
                    val incorrectAnswers = allAnswers.shuffled().take(2)
                    val questionOptions = (incorrectAnswers + correctAnswer).shuffled()

                    questionOptions
                }


                val questionsModel = QuizQuestionsModel(options,answers,previews,questions,duration)
                _questions.postValue(questionsModel)
            }catch (e:Exception){
                 Log.e("ERR",e.message.toString())
            }
        }
    }

    fun updateQuizEntity(id:Int,correct:Int,wrong:Int){
        viewModelScope.launch(Dispatchers.IO) {
            quizDao.modify(id,correct,wrong,true)
        }
    }
}