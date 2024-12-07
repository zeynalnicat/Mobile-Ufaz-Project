package com.example.finalprojectufaz.domain.track

import java.io.Serializable

data class TrackResponseModel(
    val album: Album?,
    val artist: Artist?,
    val duration: Int,
    val id: Long,
    val preview: String,
    val release_date: String,
    val title: String,
    val type: String
):Serializable
