package com.example.finalprojectufaz.ui.quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentQuizBinding


class QuizFragment : Fragment() {
    private lateinit var binding:FragmentQuizBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuizBinding.inflate(layoutInflater)
        return binding.root
    }

}