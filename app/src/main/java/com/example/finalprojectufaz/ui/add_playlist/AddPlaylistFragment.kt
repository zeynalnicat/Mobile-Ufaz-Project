package com.example.finalprojectufaz.ui.add_playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.FragmentAddPlaylistBinding
import com.example.finalprojectufaz.ui.playlist.factory.PlaylistFactory
import com.example.finalprojectufaz.ui.playlist.viewmodel.PlaylistViewModel


class AddPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentAddPlaylistBinding
    private lateinit var mActivity: MainActivity
    private lateinit var dao:PlaylistDao
    private lateinit var quizDao: QuizDao
    private val viewModel:PlaylistViewModel by viewModels { PlaylistFactory(dao,quizDao) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlaylistBinding.inflate(layoutInflater)
        val roomDB = RoomDB.accessDB(requireContext())!!
        dao = roomDB.playlistDao()
        quizDao = roomDB.quizDao()
        createPlaylist()
        mActivity = requireActivity() as MainActivity
        mActivity.hideBottomNav(true)
        setNavigation()
        return binding.root
    }

    private fun createPlaylist(){
        binding.btnCreate.setOnClickListener {
            viewModel.insert(binding.edtPlaylistName.text.toString())
            mActivity.hideBottomNav(false)
            findNavController().popBackStack()
        }
    }

    private fun setNavigation(){
        binding.btnCancel.setOnClickListener {
            mActivity.hideBottomNav(false)
            viewModel.getPlaylists()
            findNavController().popBackStack()
        }
    }

}