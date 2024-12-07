package com.example.finalprojectufaz.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.ApiService
import com.example.finalprojectufaz.data.RetrofitInstance
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val _tracks = MutableLiveData<Resource<List<TrackResponseModel>>>()
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    val tracks : LiveData<Resource<List<TrackResponseModel>>> get() = _tracks


    fun getTracks(){
        val idsMusic = listOf("89077549", "509382892", "82715364", "6461432", "1151534112", "74427068")
        val listMusic: MutableList<TrackResponseModel> = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _tracks.postValue(Resource.Loading)
                idsMusic.forEach {
                    val response = apiService.getTrack(it)
                    if(response.isSuccessful){
                        response.body()?.let { track->
                            listMusic.add(track)
                        }
                    }
                    else{
                        _tracks.postValue(Resource.Error(Exception(response.code().toString())))
                    }
                }

                _tracks.postValue(Resource.Success(listMusic))
            } catch (e:Exception){
                _tracks.postValue(Resource.Error(e))
            }

        }
    }

    fun searchTrack(name:String){
        _tracks.postValue(Resource.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val response = apiService.search(name)
                if(response.isSuccessful ){
                    response.body()?.let { track->
                        _tracks.postValue(Resource.Success(track.data))
                    }
                }else{
                    _tracks.postValue(Resource.Error(Exception(response.code().toString())))
                }
            }catch (e:Exception){
                _tracks.postValue(Resource.Error(e))
            }

        }
    }
}