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

    @Query("Delete from tracks where playlistId=:playlistId and trackId=:trackId")
    suspend fun delete(playlistId:Int , trackId:Int)

    @Query("Delete from playlists where id=:playlistId")
    suspend fun removePlaylist(playlistId:Int)

    @Query("Select name from playlists where id=:id")
    suspend fun getName(id:Int):String

}