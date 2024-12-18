package com.example.finalprojectufaz.ui.create_quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.data.local.quiz.QuizEntity
import com.example.finalprojectufaz.databinding.FragmentCreateQuizBinding
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.playlist.PlaylistDTO
import com.example.finalprojectufaz.ui.playlist.adapters.PlaylistAdapter
import com.example.finalprojectufaz.ui.playlist.factory.PlaylistFactory
import com.example.finalprojectufaz.ui.playlist.viewmodel.PlaylistViewModel
import com.example.finalprojectufaz.ui.quiz.factory.QuizFactory
import com.example.finalprojectufaz.ui.quiz.viewmodel.QuizViewModel


class CreateQuizFragment : Fragment() {
    private lateinit var binding: FragmentCreateQuizBinding
    private lateinit var mActivity:MainActivity
    private lateinit var playlistDao:PlaylistDao
    private lateinit var quizDao: QuizDao
    private val pViewModel: PlaylistViewModel by viewModels { PlaylistFactory(playlistDao,quizDao) }
    private val qViewModel: QuizViewModel by viewModels { QuizFactory(quizDao,playlistDao) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreateQuizBinding.inflate(layoutInflater)
        mActivity = requireActivity() as MainActivity
        mActivity.hideBottomNav(true)
        val roomDb = RoomDB.accessDB(requireContext())!!
        playlistDao = roomDb.playlistDao()
        quizDao = roomDb.quizDao()
        playlistDao
        setNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        pViewModel.playlists.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> {
                    setAdapter(it.data)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        })

        pViewModel.getPlaylists()
    }

    private fun createQuiz(){
        binding.btnCreate.setOnClickListener {
            val name = binding.edtQuizName.text.toString()
            val duration = when (binding.rgTimer.checkedRadioButtonId) {
                R.id.rbThirty -> 30
                R.id.rbSixty -> 60
                R.id.rbNinety -> 90
                else -> 30
            }
            val numberOfQuestions = when(binding.rgNumberQuestion.checkedRadioButtonId){
                R.id.rbThree -> 3
                R.id.rbFive -> 5
                R.id.rbSeven -> 7
                else -> 3
            }
//            quizDao.insertQuiz(QuizEntity(0,name,duration,numberOfQuestions))
        }
    }

    private fun setAdapter(playlists: List<PlaylistDTO>) {
        val adapter = PlaylistAdapter()
        adapter.submitList( playlists.map { it.copy(isBottomSheet = true) })
        binding.rvPlaylists.layoutManager = GridLayoutManager(requireContext(),1)
        binding.rvPlaylists.adapter = adapter

    }


    private fun setNavigation(){
        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()
            mActivity.hideBottomNav(false)
        }
    }
}