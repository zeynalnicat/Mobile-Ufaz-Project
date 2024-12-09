package com.example.finalprojectufaz.domain.playlist

import java.io.Serializable

data class PlaylistDTO(
    val id:Int,
    val total:Int ,
    val name:String,
    val isBottomSheet:Boolean = false,
    var isSelected:Boolean = false

):Serializable
