package com.example.finalprojectufaz.ui.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Grid
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentPlaylistBinding
import com.example.finalprojectufaz.domain.mocks.MockPlaylist
import com.example.finalprojectufaz.ui.playlist.adapters.PlaylistAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class PlaylistFragment : Fragment() {
    private lateinit var binding:FragmentPlaylistBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private lateinit var adapter: PlaylistAdapter
    private var scrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = PlaylistAdapter()
        binding = FragmentPlaylistBinding.inflate(layoutInflater)
        setAdapter()
        animBottom()
        setNavigation()
        return binding.root
    }

    private fun setAdapter(){
        val playlists = List(20){
            MockPlaylist("Playlist ${it}",it)
        }
        adapter.submitList(playlists)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
        binding.recyclerView.adapter = adapter
    }

    private fun animBottom(){
        bottomNavigationView= requireActivity().findViewById(R.id.bottomNav)

        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > this.scrollY && isBottomNavVisible) {
                hideBottomNavigation()
            } else if (scrollY < this.scrollY && !isBottomNavVisible) {
                showBottomNavigation()
            }
            this.scrollY = scrollY
        }
    }

    private fun hideBottomNavigation() {
        bottomNavigationView.animate().translationY(bottomNavigationView.height.toFloat()).start()
        isBottomNavVisible = false
    }

    private fun showBottomNavigation() {
        bottomNavigationView.animate().translationY(0f).start()
        isBottomNavVisible = true
    }

    private fun setNavigation(){
        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_playlistFragment_to_newPlaylist)
        }
    }



}