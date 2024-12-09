package com.example.finalprojectufaz.data.remote

import com.example.finalprojectufaz.domain.album.AlbumResponseModel
import com.example.finalprojectufaz.domain.album.SingleAlbumModel
import com.example.finalprojectufaz.domain.track.SearchResponseModel
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService{

    @GET(Endpoints.track)
    suspend fun getTrack(@Path("id") id:String):Response<TrackResponseModel>

    @GET(Endpoints.search_track)
    suspend fun search(@Query("q") name:String):Response<SearchResponseModel>

    @GET(Endpoints.search_album)
    suspend fun searchAlbum(@Query("q") name: String):Response<SearchResponseModel>

    @GET(Endpoints.album)
    suspend fun getAlbum(@Path("id") id:String):Response<AlbumResponseModel>

    @GET(Endpoints.single_album_tracks)
    suspend fun getSingleAlbumTracks(@Path("tracklist") albumId:String):Response<SingleAlbumModel>
}
