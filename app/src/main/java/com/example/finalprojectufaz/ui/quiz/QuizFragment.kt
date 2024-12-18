package com.example.finalprojectufaz.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.BottomSheetPlaylistBinding
import com.example.finalprojectufaz.databinding.FragmentQuizBinding
import com.example.finalprojectufaz.databinding.LayoutBottomSheetBinding
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.nav.TrackNavModel
import com.example.finalprojectufaz.domain.playlist.PlaylistDTO
import com.example.finalprojectufaz.domain.quiz.QuizDTO
import com.example.finalprojectufaz.ui.playlist.adapters.PlaylistAdapter
import com.example.finalprojectufaz.ui.quiz.adapter.QuizAdapter
import com.example.finalprojectufaz.ui.quiz.factory.QuizFactory
import com.example.finalprojectufaz.ui.quiz.viewmodel.QuizViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class QuizFragment : Fragment(),QuizAdapter.ActionCallBack {
    private lateinit var binding:FragmentQuizBinding
    private lateinit var quizDao: QuizDao
    private lateinit var playlistDao: PlaylistDao
    private val viewModel:QuizViewModel by viewModels { QuizFactory(quizDao,playlistDao) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        val roomDb = RoomDB.accessDB(requireContext())!!
        quizDao = roomDb.quizDao()
        playlistDao = roomDb.playlistDao()
        setNavigation()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.quizzes.observe(viewLifecycleOwner,{
            when(it){
                is Resource.Success -> {
                    setAdapter(it.data)
                }
                is Resource.Error -> {}
                is Resource.Loading -> {}
            }
        })
        viewModel.fetchQuizzes()
    }

    private fun setNavigation(){
        binding.btnAdd.setOnClickListener {
            findNavController().navigate(R.id.action_quizFragment_to_createQuizFragment)
        }
    }

    private fun setAdapter(quizzes: List<QuizDTO>) {
         val adapter = QuizAdapter(this)
         adapter.submitList(quizzes)
         binding.recyclerQuizzes.layoutManager = GridLayoutManager(requireContext(),1)
         binding.recyclerQuizzes.adapter = adapter
    }

    override fun nav(id: Int) {
        findNavController().navigate(QuizFragmentDirections.actionQuizFragmentToSingleQuiz(id))
    }

    override fun handleBottomSheet(id: Int) {
                val dialog = BottomSheetDialog(requireContext())
                val view = BottomSheetPlaylistBinding.inflate(layoutInflater)
                view.textView.text = "Remove quiz"
                dialog.setCancelable(true)
                dialog.setContentView(view.root)
                view.viewRemove.setOnClickListener {
                    viewModel.removeQuiz(id)
                    dialog.dismiss()
                }



                dialog.show()
            }

    }

