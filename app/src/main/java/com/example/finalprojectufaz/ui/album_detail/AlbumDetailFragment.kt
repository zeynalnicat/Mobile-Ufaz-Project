package com.example.finalprojectufaz.ui.album_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.helper.widget.Grid
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.utils.Utils
import com.example.finalprojectufaz.databinding.FragmentAlbumDetailBinding
import com.example.finalprojectufaz.domain.album.Data
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.mocks.MockAlbum
import com.example.finalprojectufaz.domain.mocks.MockTrack
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import com.example.finalprojectufaz.ui.album_detail.adapters.AlbumDetailsAdapter
import com.example.finalprojectufaz.ui.album_detail.viewmodel.AlbumDetailsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView


class AlbumDetailFragment : Fragment() {
    private lateinit var binding: FragmentAlbumDetailBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private var trackUri = ""
    private var imgUri = ""
    private lateinit var adapter: AlbumDetailsAdapter
    private val args : AlbumDetailFragmentArgs by navArgs()
    private var scrollY = 0
    private val viewModel : AlbumDetailsViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumDetailBinding.inflate(layoutInflater)
        setLayout()
        adapter = AlbumDetailsAdapter(imgUri)
        animBottom()
        setNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tracks.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> {
                    setAdapter(it.data)
                }

                is Resource.Error -> {

                }

                is Resource.Loading -> {

                }
            }
        })

        viewModel.fetchTracks(trackUri)
    }


    private fun setLayout(){
        val album = args.album
        trackUri = album.id.toString()
        imgUri = album.cover

        Glide.with(binding.root)
            .load(imgUri)
            .into(binding.imgAlbum)

        Glide.with(binding.root)
            .load(album.artistImg)
            .into(binding.imgArtist)

        binding.txtAlbumName.text = album.title
        binding.txtArtist.text = album.artist
        binding.txtDuration.text = Utils.formatSecondsToMMSS(album.duration)

    }


    private fun setAdapter(tracks:List<Data>){
        adapter.submitList(tracks)
        adapter.setNavFunction { track-> findNavController().navigate(AlbumDetailFragmentDirections.actionAlbumFragmentToDetailsFragment(track)) }
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