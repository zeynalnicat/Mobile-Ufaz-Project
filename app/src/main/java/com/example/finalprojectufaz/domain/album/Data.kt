package com.example.finalprojectufaz.domain.album

data class Data(
    val artist: Artist,
    val duration: Int,
    val id: Int,
    val preview: String,
    val title: String,
    val type: String,
    val img:String? = null
)