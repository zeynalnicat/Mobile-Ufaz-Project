package com.example.finalprojectufaz.ui.quiz.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.data.local.quiz.QuizDetailEntity
import com.example.finalprojectufaz.data.local.quiz.QuizEntity
import com.example.finalprojectufaz.data.remote.ApiService
import com.example.finalprojectufaz.data.remote.RetrofitInstance
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.quiz.QuizDTO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizViewModel(private val quizDao: QuizDao,private val playlistDao: PlaylistDao):ViewModel() {

    private val _success  = MutableLiveData<Resource<Unit>>()

    private val _quizzes = MutableLiveData<Resource<List<QuizDTO>>>()
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    val success : LiveData<Resource<Unit>> get() = _success

    val quizzes : LiveData<Resource<List<QuizDTO>>> get() = _quizzes


    fun createQuiz(quiz:QuizEntity,pIds:List<Int>,numberOfQuestions:Int){
        _success.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val id = quizDao.insertQuiz(quiz)
                val tIds = mutableListOf<String>()
                if(id!=-1L){
                    pIds.forEach {
                        val tracks = playlistDao.fetchTracks(it)
                        tracks.forEach { tId->
                            tIds.add(tId.toString())
                            }
                        }
                    }
                    tIds.shuffle()
                    var tmpCount = 0
                    while(tmpCount<numberOfQuestions){
                        val response = apiService.getTrack(tIds[tmpCount])
                        if(response.isSuccessful){
                            response.body()?.let { track->
                                val index = (0..1).random()
                                val questions = listOf("What is the name of the music?", "Who is the singer of the music")
                                val isAuthor = listOf(false,true)
                                val answers = listOf(track.title,track.artist?.name?:track.title)
                                val entity = QuizDetailEntity(0,isAuthor[index],track.preview,answers[index],id.toInt(),questions[index])
                                quizDao.insertSingleQuiz(entity)

                            }
                    }
                        tmpCount++

                }
                _success.postValue(Resource.Success(Unit))
            }catch (e:Exception){
                  _success.postValue(Resource.Error(Exception(e)))
            }



        }
    }


    fun fetchQuizzes(){
        _quizzes.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
               val quizList = quizDao.fetchAll().map { QuizDTO(it.id,it.name,it.isCompleted,it.wrongAnswers,it.correctAnswers,it.numberOfQuestions) }
               _quizzes.postValue(Resource.Success(quizList))


            }catch (e:Exception){

            }


        }
    }

    fun removeQuiz(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            quizDao.remove(id)
            fetchQuizzes()
        }
    }
}