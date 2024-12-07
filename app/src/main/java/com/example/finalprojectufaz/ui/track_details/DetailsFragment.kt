package com.example.finalprojectufaz.ui.track_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.data.utils.Utils
import com.example.finalprojectufaz.databinding.FragmentDetailsBinding
import java.util.Date


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mainActivity: MainActivity
    private val args : DetailsFragmentArgs by navArgs()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        mainActivity = requireActivity() as MainActivity
        mainActivity.hideBottomNav(true)
        setLayout()
        nav()
        return binding.root
    }

    fun nav(){
        binding.imgBack.setOnClickListener{
            mainActivity.hideBottomNav(false)
            findNavController().popBackStack()
        }
    }

    private fun setLayout(){
        val track = args.track
        binding.txtName.text = track.title
        binding.txtTrackName.text = track.artist?.name

        binding.txtEndTime.text = Utils.formatSecondsToMMSS(track.duration)

        Glide.with(binding.root)
            .load(track.album?.cover)
            .into(binding.imgTrack)

    }



}