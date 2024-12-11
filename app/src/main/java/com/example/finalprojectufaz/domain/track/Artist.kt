package com.example.finalprojectufaz.domain.track

import java.io.Serializable

data class Artist(
    val id: Int,
    val name: String,
    val picture: String
) : Serializable