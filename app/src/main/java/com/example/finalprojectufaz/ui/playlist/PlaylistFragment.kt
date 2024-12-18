package com.example.finalprojectufaz.ui.playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.BottomSheetPlaylistBinding
import com.example.finalprojectufaz.databinding.FragmentPlaylistBinding
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.playlist.PlaylistDTO
import com.example.finalprojectufaz.ui.playlist.adapters.PlaylistAdapter
import com.example.finalprojectufaz.ui.playlist.factory.PlaylistFactory
import com.example.finalprojectufaz.ui.playlist.viewmodel.PlaylistViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog


class PlaylistFragment : Fragment() {
    private lateinit var binding:FragmentPlaylistBinding
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private lateinit var adapter: PlaylistAdapter
    private lateinit var dao: PlaylistDao
    private lateinit var quizDao: QuizDao
    private val viewModel:PlaylistViewModel by viewModels {PlaylistFactory(dao,quizDao)}
    private var scrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = PlaylistAdapter({data -> setAdapterNavigation(data)},{handleBottomSheet(it)})
        binding = FragmentPlaylistBinding.inflate(layoutInflater)
        val roomDB = RoomDB.accessDB(requireContext())!!
        dao =roomDB.playlistDao()
        quizDao= roomDB.quizDao()
        animBottom()
        setNavigation()
        viewModel.getPlaylists()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.playlists.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> {
                    if(it.data.isNotEmpty()){
                        binding.txtNo.visibility = View.GONE
                        setAdapter(it.data)
                    }else{
                        binding.txtNo.visibility = View.VISIBLE
                    }

                }
                is Resource.Error -> {
                    binding.txtNo.visibility = View.VISIBLE
                }

                is Resource.Loading -> {

                }
            }
        })


    }

    private fun handleBottomSheet(playlistId:Int){
        val dialog = BottomSheetDialog(requireContext())
        val view = BottomSheetPlaylistBinding.inflate(layoutInflater)
        dialog.setCancelable(true)
        dialog.setContentView(view.root)

        view.viewRemove.setOnClickListener {
            viewModel.remove(playlistId)
            viewModel.cachePlaylists.postValue(emptyList())
        }


        dialog.show()
    }


    private fun setAdapter(playlist:List<PlaylistDTO>){
        adapter.submitList(playlist)
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

    private fun setAdapterNavigation(data:PlaylistDTO){
        viewModel.cachePlaylists.postValue(emptyList())
        findNavController().navigate(PlaylistFragmentDirections.actionPlaylistFragmentToSinglePlaylist(data))
    }

    private fun setNavigation(){
        binding.floatingActionButton.setOnClickListener {
            viewModel.cachePlaylists.postValue(emptyList())
            findNavController().navigate(R.id.action_playlistFragment_to_newPlaylist)
        }
    }



}