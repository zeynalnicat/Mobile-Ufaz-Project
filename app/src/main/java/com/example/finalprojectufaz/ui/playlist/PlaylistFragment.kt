package com.example.finalprojectufaz.ui.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentPlaylistBinding


class PlaylistFragment : Fragment() {
    private lateinit var binding:FragmentPlaylistBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistBinding.inflate(layoutInflater)
        return binding.root
    }


}