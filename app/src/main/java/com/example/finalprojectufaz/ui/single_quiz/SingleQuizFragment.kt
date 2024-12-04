package com.example.finalprojectufaz.ui.single_quiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentSingleQuizBinding


class SingleQuizFragment : Fragment() {
    private lateinit var binding:FragmentSingleQuizBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSingleQuizBinding.inflate(layoutInflater)
        return binding.root
    }

}