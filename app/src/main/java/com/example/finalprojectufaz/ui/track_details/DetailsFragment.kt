package com.example.finalprojectufaz.ui.track_details

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.finalprojectufaz.MainActivity
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.data.local.playlist.PlaylistDao
import com.example.finalprojectufaz.data.local.RoomDB
import com.example.finalprojectufaz.data.local.quiz.QuizDao
import com.example.finalprojectufaz.data.utils.Utils
import com.example.finalprojectufaz.databinding.FragmentDetailsBinding
import com.example.finalprojectufaz.databinding.LayoutBottomSheetBinding
import com.example.finalprojectufaz.domain.core.Resource
import com.example.finalprojectufaz.domain.mediaplayer.MusicPlayer
import com.example.finalprojectufaz.domain.playlist.PlaylistDTO
import com.example.finalprojectufaz.ui.playlist.adapters.PlaylistAdapter
import com.example.finalprojectufaz.ui.playlist.factory.PlaylistFactory
import com.example.finalprojectufaz.ui.playlist.viewmodel.PlaylistViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog


class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private lateinit var mainActivity: MainActivity
    private var trackUri = ""
    private var trackId :Int? = null
    private val args : DetailsFragmentArgs by navArgs()
    private val handler = Handler(Looper.getMainLooper())
    private var currentPosition = 0
    private var isPlaying = false
    private lateinit var dao: PlaylistDao
    private lateinit var quizDao: QuizDao
    private val pViewModel: PlaylistViewModel by viewModels { PlaylistFactory(dao,quizDao) }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        val roomDB =RoomDB.accessDB(requireContext())!!
        dao = roomDB.playlistDao()
        quizDao = roomDB.quizDao()
        mainActivity = requireActivity() as MainActivity
        mainActivity.hideBottomNav(true)
        setLayout()
        setupListeners()
        setupSeekBar()
        nav()
        handleBottomSheet(trackId!!)
        return binding.root
    }

    private fun handleBottomSheet(trackId:Int){
        binding.btnMore.setOnClickListener {
            pViewModel.getPlaylists()
            val dialog = BottomSheetDialog(requireContext())
            val view = LayoutBottomSheetBinding.inflate(layoutInflater)
            dialog.setCancelable(true)
            dialog.setContentView(view.root)
            val pAdapter = PlaylistAdapter()

            pViewModel.playlists.observe(viewLifecycleOwner,{
                when(it){
                    is Resource.Success->{
                        pAdapter.submitList(it.data.map { PlaylistDTO(it.id,0,it.name,true) })
                        view.recyclerView.layoutManager = GridLayoutManager(requireContext(),1)
                        view.recyclerView.adapter = pAdapter
                    }

                    is Resource.Error -> {}
                    Resource.Loading -> {}
                }
            })

            view.btnAdd.setOnClickListener {
                val ids = pAdapter.getSelected()
                pViewModel.addToPlaylists(trackId,ids)
                dialog.dismiss()
            }



            dialog.show()
        }
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
                currentPosition = MusicPlayer.getInstance().getCurrentPosition()
                binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_play))
                isPlaying = false
            }
        }
    }

    private fun setupSeekBar() {
        binding.progressBar.setOnTouchListener { _, event ->
            if (event.action == android.view.MotionEvent.ACTION_DOWN || event.action == android.view.MotionEvent.ACTION_MOVE) {
                val newPosition = (event.x / binding.progressBar.width) * MusicPlayer.getInstance().getDuration()
                MusicPlayer.getInstance().seekTo(newPosition.toInt())
                binding.progressBar.progress = newPosition.toInt()
            }
            true
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
        trackId = track.id.toInt()
        binding.txtName.text = track.title
        binding.txtTrackName.text = track.title
        binding.txtArtist.text = track.artist

        Glide.with(binding.root)
            .load(track.img)
            .into(binding.imgTrack)

        startNewTrack()

    }
    private fun startNewTrack() {
        MusicPlayer.getInstance().stopMusic()
        MusicPlayer.getInstance().startMusic(requireContext(), trackUri)
        startProgressBar()
        binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(requireContext(), R.drawable.icon_pause))
        isPlaying = true
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