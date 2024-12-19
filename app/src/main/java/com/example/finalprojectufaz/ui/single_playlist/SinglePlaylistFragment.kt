package com.example.finalprojectufaz.ui.single_playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.BottomSheetPlaylistBinding
import com.example.finalprojectufaz.databinding.FragmentSinglePlaylistBinding
import com.example.finalprojectufaz.domain.album.Artist
import com.example.finalprojectufaz.domain.album.Data
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.nav.TrackNavModel
import com.example.finalprojectufaz.domain.track.TrackResponseModel
import com.example.finalprojectufaz.ui.album_detail.adapters.AlbumDetailsAdapter
import com.example.finalprojectufaz.ui.single_playlist.factory.SinglePlaylistFactory
import com.example.finalprojectufaz.ui.single_playlist.view_model.SinglePlaylistViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.bottomsheet.BottomSheetDialog


class SinglePlaylistFragment : Fragment() {
    private lateinit var binding:FragmentSinglePlaylistBinding
    private lateinit var mActivity : MainActivity
    private lateinit var bottomNavigationView: BottomNavigationView
    private var isBottomNavVisible = true
    private val args:SinglePlaylistFragmentArgs by navArgs()
    private lateinit var dao: PlaylistDao
    private lateinit var quizDao: QuizDao
    private val viewModel:SinglePlaylistViewModel by viewModels { SinglePlaylistFactory(dao,quizDao) }
    private var playlistId : Int? = null
    private var scrollY = 0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSinglePlaylistBinding.inflate(layoutInflater)
        mActivity = requireActivity() as MainActivity
        playlistId = args.playlist.id
        binding.txtPlaylistName.text = args.playlist.name
        val roomDb = RoomDB.accessDB(requireContext())!!
        dao = roomDb.playlistDao()
        quizDao = roomDb.quizDao()
        setNavigation()
        animBottom()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.tracks.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success ->{
                    setAdapter(it.data)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        })

        viewModel.fetchTracks(playlistId!!)
    }

    private fun setAdapter(data: List<TrackResponseModel>) {
        val trackModel = data.map { Data(artist = Artist(id = it.artist?.id ?:0, name = it.artist?.name?:"", picture = it.artist?.picture?:""), duration = it.duration, id = it.id.toInt()
            , preview = it.preview, title = it.title, type = it.type, img = it.album.cover) }
        val adapter = AlbumDetailsAdapter(action = {track -> handleBottomSheet(track)})
        adapter.setNavFunction { findNavController().navigate(SinglePlaylistFragmentDirections.actionSinglePlaylistToDetailsFragment(it)) }
        adapter.submitList(trackModel)
        binding.recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
        binding.recyclerView.adapter = adapter
    }

    private fun setNavigation(){
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun handleBottomSheet(trackNavModel: TrackNavModel){
        val dialog = BottomSheetDialog(requireContext())
        val view = BottomSheetPlaylistBinding.inflate(layoutInflater)
        dialog.setCancelable(true)
        dialog.setContentView(view.root)

        view.viewRemove.setOnClickListener {
            viewModel.removeTrack(trackNavModel.id.toInt(),playlistId!!.toInt())
            dialog.dismiss()

        }

        dialog.show()
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