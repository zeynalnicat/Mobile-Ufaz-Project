package com.example.finalprojectufaz.data.local.playlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query


@Dao
interface PlaylistDao {

    @Insert
    suspend fun addPlaylist(playlistEntity: PlaylistEntity):Long

    @Query("Select * from playlists ")
    suspend fun getPlaylists():List<PlaylistEntity>

    @Insert
    suspend fun insertPlaylist(trackEntity: TrackEntity):Long

    @Query("Select trackId from tracks where playlistId=:playlistId ")
    suspend fun fetchTracks(playlistId:Int):List<Int>

    @Query("Select Count(*) from tracks where playlistId=:playlistId")
    suspend fun getCount(playlistId:Int):Int


}