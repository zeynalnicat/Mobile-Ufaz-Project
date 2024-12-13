package com.example.finalprojectufaz.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentSearchBinding
import com.example.finalprojectufaz.domain.ChipState
import com.example.finalprojectufaz.domain.ResponseModel
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.ui.search.adapters.TracksListAdapter
import com.example.finalprojectufaz.ui.search.viewmodel.SearchViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class SearchFragment : Fragment() {
    private lateinit var binding:FragmentSearchBinding
    private lateinit var adapter: TracksListAdapter
    private val viewModel : SearchViewModel by viewModels()
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private var scrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(layoutInflater)
        adapter = TracksListAdapter()
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.adapter=adapter

        animBottom()
        handleChip()


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        viewModel.tracks.observe(viewLifecycleOwner,{
            when(it){
               is  Resource.Success -> {
                   binding.progressBar.visibility = View.GONE
                   setAdapter(it.data)
               }
                is Resource.Error -> {
                    binding.progressBar.visibility = View.GONE
                    Snackbar.make(requireView(),it.exception.message?:"Something Unexpected",Snackbar.LENGTH_SHORT).show()
                }

                is Resource.Loading ->{
                    binding.progressBar.visibility = View.VISIBLE
                }


            }

        })

        viewModel.chipsState.observe(viewLifecycleOwner,{
            when(it){
                ChipState.TRACK -> {
                    binding.chipTrack.chipBackgroundColor =
                        ContextCompat.getColorStateList(requireContext(), R.color.white)
                    binding.chipAlbum.chipBackgroundColor =
                        ContextCompat.getColorStateList(requireContext(), R.color.primary)
                    viewModel.getTracks()
                    setSearch(ChipState.TRACK)

                }

                ChipState.ALBUM -> {
                    binding.chipTrack.chipBackgroundColor =
                        ContextCompat.getColorStateList(requireContext(), R.color.primary)
                    binding.chipAlbum.chipBackgroundColor =
                        ContextCompat.getColorStateList(requireContext(), R.color.white)
                    viewModel.getAlbums()
                    setSearch(ChipState.ALBUM)
                }
            }
        })
    }

    private fun setAdapter(tracks: List<ResponseModel>) {
        adapter.setNavTrack { data ->
                findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(data))
        }

        adapter.setNavAlbum { data->
            findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToAlbumFragment(data))
        }
        adapter.submitList(tracks)
    }

    private fun handleChip(){
        binding.chipAlbum.setOnClickListener {
            viewModel.setChipState(ChipState.ALBUM)

        }

        binding.chipTrack.setOnClickListener {
            viewModel.setChipState(ChipState.TRACK)
        }



            }




    private fun setSearch(state: ChipState){
        if(state==ChipState.TRACK){
            binding.edtSearch.doAfterTextChanged {
                    it?.let { src ->
                        if (src.toString().isNotEmpty()) {
                            viewModel.search(src.toString(), ChipState.TRACK)
                        }else{
                            viewModel.getTracks()
                        }
                }
            }
            }
       else{
           binding.edtSearch.doAfterTextChanged {
                it?.let { src ->
                    if (src.toString().isNotEmpty()) {
                        viewModel.search(src.toString(), ChipState.ALBUM)
                    }else{
                        viewModel.getAlbums()
                    }
                }

            }
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