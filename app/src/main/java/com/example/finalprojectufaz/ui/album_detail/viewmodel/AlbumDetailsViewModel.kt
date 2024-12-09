package com.example.finalprojectufaz.ui.album_detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.ApiService
import com.example.finalprojectufaz.data.RetrofitInstance
import com.example.finalprojectufaz.domain.album.Data
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AlbumDetailsViewModel:ViewModel() {

    private val _tracks = MutableLiveData<Resource<List<Data>>>()
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    val tracks:LiveData<Resource<List<Data>>> get() = _tracks


    fun fetchTracks(id:String){
        _tracks.postValue(Resource.Loading)

        viewModelScope.launch(Dispatchers.IO) {
            try {
              val response = apiService.getSingleAlbumTracks(id)
                if(response.isSuccessful){
                    response.body()?.let { track->
                        _tracks.postValue(Resource.Success(track.data))
                    }
                }else{
                    _tracks.postValue(Resource.Error(Exception(response.message())))
                }

            }catch (e:Exception){
                _tracks.postValue(Resource.Error(e))
            }
        }
    }

}