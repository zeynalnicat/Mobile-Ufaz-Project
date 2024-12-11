package com.example.finalprojectufaz.domain.album

import com.example.finalprojectufaz.domain.ResponseModel
import com.example.finalprojectufaz.domain.track.Artist
import java.io.Serializable

data class AlbumResponseModel(
    val artist: Artist?=null,
    val available: Boolean = false,
    val cover: String,
    val duration: Int = 0,
    val id: Int,
    val nb_tracks: Int =0,
    val record_type: String = "",
    val release_date: String ="",
    val title: String,
    val tracklist: String
):Serializable , ResponseModel()
