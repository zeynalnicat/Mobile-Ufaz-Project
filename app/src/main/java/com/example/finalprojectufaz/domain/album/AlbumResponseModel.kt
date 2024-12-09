package com.example.finalprojectufaz.domain.album

import com.example.finalprojectufaz.domain.ResponseModel
import com.example.finalprojectufaz.domain.track.Artist

data class AlbumResponseModel(
    val artist: Artist,
    val available: Boolean,
    val cover: String,
    val duration: Int,
    val id: Int,
    val nb_tracks: Int,
    val record_type: String,
    val release_date: String,
    val title: String,
    val tracklist: String
):ResponseModel()