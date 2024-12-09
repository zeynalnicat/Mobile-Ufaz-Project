package com.example.finalprojectufaz.ui.single_playlist.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.remote.ApiService
import com.example.finalprojectufaz.data.remote.RetrofitInstance
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import kotlinx.coroutines.launch

class SinglePlaylistViewModel(private val dao:PlaylistDao):ViewModel() {

    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)

    private val _tracks = MutableLiveData<Resource<List<TrackResponseModel>>>()

    val tracks : LiveData<Resource<List<TrackResponseModel>>> get()=_tracks


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
}