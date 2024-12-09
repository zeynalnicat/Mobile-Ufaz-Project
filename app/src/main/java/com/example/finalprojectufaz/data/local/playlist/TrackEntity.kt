package com.example.finalprojectufaz.data.local.playlist

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey


@Entity("Tracks", foreignKeys = [ ForeignKey(
    entity = PlaylistEntity::class,
    parentColumns = ["id"],
    childColumns = ["playlistId"],
    onDelete = ForeignKey.CASCADE
)])
data class TrackEntity (
    @PrimaryKey(autoGenerate = true)
    val id : Int=0,
    val trackId:Int,
    val playlistId : Int,
)