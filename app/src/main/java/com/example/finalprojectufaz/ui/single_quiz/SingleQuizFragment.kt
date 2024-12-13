package com.example.finalprojectufaz.ui.single_quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.FragmentSingleQuizBinding
import com.example.finalprojectufaz.ui.single_quiz.adapter.QuizPagerAdapter
import com.example.finalprojectufaz.ui.single_quiz.factory.SingleQuizFactory
import com.example.finalprojectufaz.ui.single_quiz.viewmodel.SingleQuizViewModel


class SingleQuizFragment : Fragment(),QuizPagerAdapter.ActionCallBac {
    private lateinit var binding:FragmentSingleQuizBinding
    private val args:SingleQuizFragmentArgs by navArgs()
    private lateinit var quizDao: QuizDao
    private val viewModel:SingleQuizViewModel by viewModels { SingleQuizFactory(quizDao) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleQuizBinding.inflate(layoutInflater)
        quizDao = RoomDB.accessDB(requireContext())!!.quizDao()
        binding.viewPager.isUserInputEnabled = false
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.questions.observe(viewLifecycleOwner,{
              if(it!=null){
                  val adapter = QuizPagerAdapter(it,this)
                  binding.viewPager.adapter=adapter
              }

        })

        viewModel.fetchQuizQuestions(args.id)

    }

    override fun nav() {
        findNavController().popBackStack()
    }

    override fun next() {
        val currentItem = binding.viewPager.currentItem
        val itemCount = binding.viewPager.adapter?.itemCount ?: 0
        if (currentItem < itemCount - 1) {
            binding.viewPager.setCurrentItem(currentItem + 1, true)
        }else{
            findNavController().navigate(SingleQuizFragmentDirections.actionSingleQuizToQuizResultFragment(args.id))
        }
    }

    override fun back() {
        val currentItem = binding.viewPager.currentItem
        if(currentItem!=0){
            binding.viewPager.setCurrentItem(currentItem-1,true)
        }
    }


}