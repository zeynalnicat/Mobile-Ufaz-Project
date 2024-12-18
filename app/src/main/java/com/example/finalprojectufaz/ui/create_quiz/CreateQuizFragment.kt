package com.example.finalprojectufaz.ui.create_quiz

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
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
    private val adapter = PlaylistAdapter()
    private lateinit var quizDao: QuizDao
    private var duration= 0
    private var numberOfQuestion = 0
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
        binding.rgTimer.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1) {
                val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedId)
                val selectedText = selectedRadioButton.text.toString()
                duration = selectedText.split(" ")[0].toInt()
            }
        }

        binding.rgNumberQuestion.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId!=-1){
                val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedId)
                numberOfQuestion = selectedRadioButton.text.toString().toInt()
            }
        }

        createQuiz()
        pViewModel.getPlaylists()
    }

    private fun createQuiz(){
        binding.btnCreate.setOnClickListener {
            val mapPlaylist = adapter.getSelected()
            val name = binding.edtQuizName.text.toString()

            if(numberOfQuestion > mapPlaylist["count"] as Int){
                Toast.makeText(requireContext(),"Not enough track in playlist(s)",Toast.LENGTH_SHORT).show()
            }
            else{
                if(name.isNotEmpty() && duration!=0 && numberOfQuestion!=0){
                    (mapPlaylist["selectedList"] as? MutableList<Int>)?.let { it1 ->
                        qViewModel.createQuiz(QuizEntity(0,name,duration,numberOfQuestion),
                            it1,numberOfQuestion
                        )
                    }
                    qViewModel.success.observe(viewLifecycleOwner,{
                        when(it){
                            is Resource.Success -> {
                                mActivity.hideBottomNav(false)
                                findNavController().popBackStack()
                            }
                            is Resource.Loading -> { binding.progressBar.visibility = View.VISIBLE}
                            is Resource.Error -> {}
                        }
                    })
                }else{
                    Toast.makeText(requireContext(),"Fill required inputs",Toast.LENGTH_SHORT).show()
                }
            }




        }
    }

    private fun setAdapter(playlists: List<PlaylistDTO>) {
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