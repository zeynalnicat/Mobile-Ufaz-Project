package com.example.finalprojectufaz.ui.track_details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.utils.Utils
import com.example.finalprojectufaz.databinding.FragmentDetailsBinding
import com.example.finalprojectufaz.domain.mediaplayer.MusicPlayer
import java.util.Date


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mainActivity: MainActivity
    private var trackUri = ""
    private val args : DetailsFragmentArgs by navArgs()
    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0
    private var isPlaying = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        mainActivity = requireActivity() as MainActivity
        mainActivity.hideBottomNav(true)
        setLayout()
        setupListeners()
        nav()
        return binding.root
    }

    private fun setupListeners() {
        currentPosition = MusicPlayer.getInstance().getCurrentPosition()
        binding.btnPlay.setOnClickListener {
            if (!isPlaying) {
                if (currentPosition > 0) {
                    MusicPlayer.getInstance().resumeMusic(requireContext(), trackUri, currentPosition)
                } else {
                    MusicPlayer.getInstance().startMusic(requireContext(), trackUri)
                }
                startProgressBar()
                binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_pause))
                isPlaying = true
            } else {
                MusicPlayer.getInstance().pauseMusic()
                currentPosition = MusicPlayer.getInstance().getCurrentPosition() // Store the current position
                binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_play))
                isPlaying = false
            }
        }
    }

    private fun startProgressBar() {
        handler.post(object : Runnable {
            override fun run() {
                val currentPosition = MusicPlayer.getInstance().getCurrentPosition()
                val totalDuration = MusicPlayer.getInstance().getDuration()

                binding.progressBar.max = totalDuration
                binding.progressBar.progress = currentPosition
                binding.txtCurrentTime.text = Utils.formatSecondsToMMSS(currentPosition / 1000)

                if (currentPosition < totalDuration) {
                    handler.postDelayed(this, 500)
                } else {
                    stopProgressBarUpdates()
                    isPlaying = false
                }
            }
        })
    }


    fun nav(){
        binding.imgBack.setOnClickListener{
            mainActivity.hideBottomNav(false)
            findNavController().popBackStack()
        }
    }

    private fun setLayout(){
        val track = args.track
        trackUri = track.preview
        binding.txtName.text = track.title
        binding.txtTrackName.text = track.title
        binding.txtArtist.text = track.artist

        Glide.with(binding.root)
            .load(track.img)
            .into(binding.imgTrack)


    }

    private fun stopProgressBarUpdates() {
        handler.removeCallbacksAndMessages(null)
        binding.progressBar.progress = 0
        binding.txtCurrentTime.text = "00:00"
        binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(),R.drawable.icon_play))
    }

    override fun onPause() {
        super.onPause()
        stopProgressBarUpdates()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopProgressBarUpdates()
    }



}