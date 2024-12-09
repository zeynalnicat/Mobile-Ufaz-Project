package com.example.finalprojectufaz.domain.track

import com.example.finalprojectufaz.domain.ResponseModel
import com.example.finalprojectufaz.domain.album.AlbumResponseModel
import java.io.Serializable

data class TrackResponseModel(
    val album: AlbumResponseModel,
    val artist: Artist?,
    val duration: Int,
    val id: Long,
    val preview: String,
    val release_date: String,
    val title: String,
    val type: String
):Serializable, ResponseModel()
