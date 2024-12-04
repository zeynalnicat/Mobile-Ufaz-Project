package com.example.finalprojectufaz.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.ActionBar.LayoutParams
import androidx.constraintlayout.helper.widget.Grid
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentSearchBinding
import com.example.finalprojectufaz.domain.mocks.MockTrack
import com.example.finalprojectufaz.ui.search.adapters.TracksListAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter: TracksListAdapter
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private var scrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        adapter = TracksListAdapter{nav()}

        setAdapter()
        handleChip()

         bottomNavigationView= requireActivity().findViewById(R.id.bottomNav)

        binding.nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, _ ->
            if (scrollY > this.scrollY && isBottomNavVisible) {
                hideBottomNavigation()
            } else if (scrollY < this.scrollY && !isBottomNavVisible) {
                showBottomNavigation()
            }
            this.scrollY = scrollY
        }
        return binding.root
    }

    private fun hideBottomNavigation() {
        bottomNavigationView.animate().translationY(bottomNavigationView.height.toFloat()).start()
        isBottomNavVisible = false
    }

    private fun showBottomNavigation() {
        bottomNavigationView.animate().translationY(0f).start()
        isBottomNavVisible = true
    }

    private fun setAdapter(){
        val items = List(10) {
            MockTrack("Track ${it}","https://picsum.photos/200/300")
        }
        adapter.submitList(items)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.adapter=adapter
    }

    private fun nav(){
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment)
    }

    private fun handleChip(){
        binding.chipAlbum.setOnClickListener {
            val albumItems = List(10) {
            MockTrack("Album $it", "https://picsum.photos/200/300")
        }
            requireActivity().runOnUiThread {
                adapter.submitList(albumItems)
            } }

        binding.chipTrack.setOnClickListener {
            val items = List(10) {
                MockTrack("Track ${it}","https://picsum.photos/200/300")
            }
            adapter.submitList(items)
        }

        }
}