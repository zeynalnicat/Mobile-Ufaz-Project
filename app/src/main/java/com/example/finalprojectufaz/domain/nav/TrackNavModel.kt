package com.example.finalprojectufaz.domain.nav

import java.io.Serializable

data class TrackNavModel(
    val id : Long ,
    val img : String,
    val title: String ,
    val artist:String ,
    val preview: String,
    val duration : Int
):Serializable
