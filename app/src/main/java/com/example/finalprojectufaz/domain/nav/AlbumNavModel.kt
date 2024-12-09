package com.example.finalprojectufaz.domain.nav

import java.io.Serializable

data class AlbumNavModel(
    val id:Int ,
    val title:String,
    val artistImg : String,
    val duration:Int,
    val artist:String,
    val cover:String ,
    val tracksUri : String
):Serializable
