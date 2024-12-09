package com.example.finalprojectufaz.ui.single_playlist.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.ui.single_playlist.view_model.SinglePlaylistViewModel

class SinglePlaylistFactory(private val dao: PlaylistDao):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SinglePlaylistViewModel(dao) as T
    }
}