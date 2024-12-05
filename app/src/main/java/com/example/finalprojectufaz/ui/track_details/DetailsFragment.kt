package com.example.finalprojectufaz.ui.track_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mainActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        mainActivity = requireActivity() as MainActivity
        mainActivity.hideBottomNav(true)
        nav()
        return binding.root
    }

    fun nav(){
        binding.imgBack.setOnClickListener{
            mainActivity.hideBottomNav(false)
            findNavController().popBackStack()
        }
    }

}