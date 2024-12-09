package com.example.finalprojectufaz.ui.playlist.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.ui.playlist.viewmodel.PlaylistViewModel

class PlaylistFactory(private val playlistDao: PlaylistDao):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlaylistViewModel(playlistDao) as T
    }
}