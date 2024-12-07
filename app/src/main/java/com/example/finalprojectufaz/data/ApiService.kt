package com.example.finalprojectufaz.data

import com.example.finalprojectufaz.domain.track.SearchResponseModel
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET(Endpoints.track)
    suspend fun getTrack(@Path("id") id:String):Response<TrackResponseModel>

    @GET(Endpoints.search)
    suspend fun search(@Query("q") name:String):Response<SearchResponseModel>
}
