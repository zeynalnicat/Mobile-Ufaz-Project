package com.example.finalprojectufaz.ui.add_playlist

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentAddPlaylistBinding


class AddPlaylistFragment : Fragment() {
    private lateinit var binding: FragmentAddPlaylistBinding
    private lateinit var mActivity: MainActivity


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddPlaylistBinding.inflate(layoutInflater)
        mActivity = requireActivity() as MainActivity
        mActivity.hideBottomNav(true)
        setNavigation()
        return binding.root
    }


    private fun setNavigation(){
        binding.btnCancel.setOnClickListener {
            mActivity.hideBottomNav(false)
            findNavController().popBackStack()
        }
    }

}