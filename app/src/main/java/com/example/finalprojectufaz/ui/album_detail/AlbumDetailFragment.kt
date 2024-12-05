package com.example.finalprojectufaz.ui.album_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Grid
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentAlbumDetailBinding
import com.example.finalprojectufaz.domain.mocks.MockAlbum
import com.example.finalprojectufaz.domain.mocks.MockTrack
import com.example.finalprojectufaz.ui.album_detail.adapters.AlbumDetailsAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView


class AlbumDetailFragment : Fragment() {
    private lateinit var binding: FragmentAlbumDetailBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private lateinit var adapter: AlbumDetailsAdapter
    private var scrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailBinding.inflate(layoutInflater)
        adapter = AlbumDetailsAdapter()
        Glide.with(binding.root)
            .load("https://picsum.photos/200/300")
            .into(binding.imgAlbum)
        setAdapter()
        animBottom()
        setNavigation()
        return binding.root
    }


    private fun setAdapter(){
        val tracks = List(10){
            MockTrack("Track ${it}","https://picsum.photos/200/300")
        }
        adapter.submitList(tracks)
        adapter.setNavFunction { findNavController().navigate(R.id.action_albumFragment_to_detailsFragment) }
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
        binding.recyclerView.adapter = adapter
    }


    private fun setNavigation(){
        binding.btnBack.setOnClickListener{
            findNavController().popBackStack()
        }

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
}