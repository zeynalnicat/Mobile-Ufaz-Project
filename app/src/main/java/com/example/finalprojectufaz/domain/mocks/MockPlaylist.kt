package com.example.finalprojectufaz.domain.mocks

data class MockPlaylist(
    val name: String ,
    val count: Int ,
    val tracks : List<MockTrack>? = null

)
