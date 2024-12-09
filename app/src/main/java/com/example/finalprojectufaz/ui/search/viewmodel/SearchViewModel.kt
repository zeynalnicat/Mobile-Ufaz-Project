package com.example.finalprojectufaz.ui.search.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.finalprojectufaz.data.remote.ApiService
import com.example.finalprojectufaz.data.remote.RetrofitInstance
import com.example.finalprojectufaz.domain.ChipState
import com.example.finalprojectufaz.domain.ResponseModel
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel: ViewModel() {
    private val _tracks = MutableLiveData<Resource<List<out ResponseModel>>>()
    private val apiService = RetrofitInstance.getInstance().create(ApiService::class.java)
    private val _chipState = MutableLiveData(ChipState.TRACK)
    private val _cachedTracks = MutableLiveData<List<ResponseModel>>()
    private val _cachedAlbums = MutableLiveData<List<ResponseModel>>()


    val tracks : LiveData<Resource<List<ResponseModel>>> get() = _tracks
    val chipsState : LiveData<ChipState> get() = _chipState

    fun setChipState(state: ChipState){
        _chipState.postValue(state)
    }


    fun getTracks(){
        if (!_cachedTracks.value.isNullOrEmpty()) {
            _tracks.postValue(Resource.Success(_cachedTracks.value!!))
            return
        }

        val idsMusic = listOf("89077549", "509382892", "82715364", "6461432", "1151534112", "74427068","1105744","1102744")
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

                _cachedTracks.postValue(listMusic)
                _tracks.postValue(Resource.Success(listMusic))
            } catch (e:Exception){
                _tracks.postValue(Resource.Error(e))
            }

        }
    }

    fun getAlbums(){

        if (!_cachedAlbums.value.isNullOrEmpty()) {

            _tracks.postValue(Resource.Success(_cachedAlbums.value!!))
            return
        }
        val idsAlbum = listOf("302127","988431","119606","321254","321255","321259")
        val listAlbum: MutableList<ResponseModel> = mutableListOf()

        viewModelScope.launch(Dispatchers.IO) {
            try {
                _tracks.postValue(Resource.Loading)
                idsAlbum.forEach {
                    val response = apiService.getAlbum(it)
                    if(response.isSuccessful){
                        response.body()?.let { album->
                            listAlbum.add(album)
                        }
                    }
                    else{

                        _tracks.postValue(Resource.Error(Exception(response.code().toString())))
                    }
                }
                _cachedAlbums.postValue(listAlbum)
                _tracks.postValue(Resource.Success(listAlbum))
            }catch (e:Exception){
                _tracks.postValue(Resource.Error(e))
            }
        }

    }

    fun search(name:String, state: ChipState){
        _tracks.postValue(Resource.Loading)
        if(state == ChipState.TRACK){
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
        }else if (state == ChipState.ALBUM){
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = apiService.searchAlbum(name)
                    if(response.isSuccessful ){
                        response.body()?.let { track->
//                            _tracks.postValue(Resource.Success(
//                                track.data.map { AlbumResponseModel(artist = it.artist, cover = it.album.cover, nb_tracks = it.album.) }
//                            ))
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

}