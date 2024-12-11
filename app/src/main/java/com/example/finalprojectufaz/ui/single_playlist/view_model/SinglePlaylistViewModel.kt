package com.example.finalprojectufaz.ui.single_playlist.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.data.local.quiz.QuizEntity
import com.example.finalprojectufaz.data.remote.ApiService
import com.example.finalprojectufaz.data.remote.RetrofitInstance
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SinglePlaylistViewModel(private val dao:PlaylistDao,private val quizDao: QuizDao):ViewModel() {

    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    private val _tracks = MutableLiveData<Resource<List<TrackResponseModel>>>()


    val tracks : LiveData<Resource<List<TrackResponseModel>>> get()=_tracks


    fun quizExist(playlistId: Int){
        viewModelScope.launch(Dispatchers.IO) {
            val count = quizDao.count(playlistId)

            if(count>0){

            }
            else{
                val entity = QuizEntity(0,playlistId,null,null,false)
                quizDao.insertQuiz(entity)
            }
        }
    }




    fun fetchTracks(id:Int){

        _tracks.postValue(Resource.Loading)
        viewModelScope.launch {
            try {
                val ids = dao.fetchTracks(id)
                val listTracks = mutableListOf<TrackResponseModel>()

                if(ids.isNotEmpty()){
                    ids.forEach {
                        val response = apiService.getTrack(it.toString())
                        if(response.isSuccessful){
                            response.body()?.let { track->
                                listTracks.add(track)
                            }
                        }
                    }
                    _tracks.postValue(Resource.Success(listTracks))

                }
            }catch (e:Exception){
                _tracks.postValue(Resource.Error(e))
            }

        }
    }


    fun removeTrack(trackId:Int,playlistId:Int){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                dao.delete(playlistId,trackId)
                fetchTracks(playlistId)
            }catch (e:Exception){
               Log.e("Error",e.message.toString())
            }
        }
    }
}