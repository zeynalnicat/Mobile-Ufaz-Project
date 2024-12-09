package com.example.finalprojectufaz.data.local.playlist

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity("Playlists")
data class PlaylistEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int =0,
    val name:String
)
