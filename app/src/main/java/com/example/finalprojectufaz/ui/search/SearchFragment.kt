package com.example.finalprojectufaz.ui.search

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentSearchBinding
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.mocks.MockTrack
import com.example.finalprojectufaz.domain.track.TrackResponseModel
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
        adapter.setNavFunction { data -> findNavController().navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(data))}
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.recyclerView.adapter=adapter

        animBottom()


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
        setSearch()
        viewModel.getTracks()

    }

    private fun setAdapter(tracks:List<TrackResponseModel>){
        adapter.submitList(tracks)

    }

    private fun navToAlbum(){
        findNavController().navigate(R.id.action_searchFragment_to_albumFragment)
    }

//    private fun handleChip(){
//        binding.chipAlbum.setOnClickListener {
//            val albumItems = List(10) {
//            MockTrack("Album $it", "https://cdn-images.dzcdn.net/images/cover/44c0a2696951b044e65d0e33d65a5d32/0x1900-000000-80-0-0.jpg")
//
//        }
//         adapter.submitList(albumItems)
//         adapter.setNavFunction { navToAlbum() }
//            }
//
//        binding.chipTrack.setOnClickListener {
//            val items = List(10) {
//                MockTrack("Track ${it}","https://c.saavncdn.com/editorial/Let_sPlayAlanWalker_20241122142442.jpg")
//            }
//            adapter.submitList(items)
//            adapter.setNavFunction { navToTrack() }
//        }
//
//        }

    private fun setSearch(){
        binding.edtSearch.doAfterTextChanged {
            it?.let { src->
                if(src.toString().isNotEmpty()){
                    viewModel.searchTrack(src.toString())
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