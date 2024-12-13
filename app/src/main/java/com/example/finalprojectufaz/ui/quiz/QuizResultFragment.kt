package com.example.finalprojectufaz.ui.quiz

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.databinding.FragmentQuizResultBinding
import com.example.finalprojectufaz.domain.quiz.QuizQuestionsModel
import com.example.finalprojectufaz.ui.single_quiz.factory.SingleQuizFactory
import com.example.finalprojectufaz.ui.single_quiz.viewmodel.SingleQuizViewModel

class QuizResultFragment : Fragment() {
    private lateinit var binding:FragmentQuizResultBinding
    private val args:QuizResultFragmentArgs by navArgs()
    private lateinit var quizDao:QuizDao
    private val handler = Handler()
    private val viewModel:SingleQuizViewModel by viewModels { SingleQuizFactory(quizDao) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizResultBinding.inflate(layoutInflater)
        binding.txtNumberCorrect.text = QuizQuestionsModel.correctAnswers.toString()
        binding.txtNumberWrong.text = QuizQuestionsModel.wrongAnswers.toString()
        quizDao = RoomDB.accessDB(requireContext())!!.quizDao()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.updateQuizEntity(args.id,QuizQuestionsModel.correctAnswers,QuizQuestionsModel.wrongAnswers)
        QuizQuestionsModel.correctAnswers = 0
        QuizQuestionsModel.wrongAnswers = 0

        handler.postDelayed({
            findNavController().navigate(R.id.action_quizResultFragment_to_quizFragment)
        },3000)

    }

}