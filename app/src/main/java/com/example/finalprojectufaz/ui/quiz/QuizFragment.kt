package com.example.finalprojectufaz.ui.quiz

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.FragmentQuizBinding
import com.example.finalprojectufaz.domain.playlist.PlaylistDTO
import com.example.finalprojectufaz.domain.track.TrackResponseModel

class QuizFragment : Fragment() {

    private lateinit var binding: FragmentQuizBinding
    private lateinit var mediaPlayer: MediaPlayer
    data class Question(
        val question: String,
        val correctAnswer: String,
        val songUri: String
    )
    private lateinit var questions: List<Question>
    private var currentIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentQuizBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Retrieve the tracks from arguments
        val tracks = arguments?.getSerializable("tracks") as? Array<TrackResponseModel>
        if (tracks == null) {
            Toast.makeText(requireContext(), "No tracks available for the quiz", Toast.LENGTH_SHORT).show()
            return
        }

        questions = generateQuestions(tracks.toList())
        displayQuestion(currentIndex)

        binding.btnPlayMusic.setOnClickListener {
            playMusic(questions[currentIndex].songUri)
        }

        binding.btnAnswerA.setOnClickListener {
            checkAnswer(questions[currentIndex], "A")
        }

        binding.btnAnswerB.setOnClickListener {
            checkAnswer(questions[currentIndex], "B")
        }

        binding.btnNext.setOnClickListener {
            if (currentIndex < questions.size - 1) {
                currentIndex++
                displayQuestion(currentIndex)
            } else {
                Toast.makeText(requireContext(), "This is the last question", Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnPrevious.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex--
                displayQuestion(currentIndex)
            } else {
                Toast.makeText(requireContext(), "This is the first question", Toast.LENGTH_SHORT).show()
            }
        }
    }


//    private fun getAlbumSongs(): List<PlaylistDTO> {
//        // Replace this mock data with real data fetched via arguments or ViewModel
//        return listOf(
//            PlaylistDTO(1, "Song 1", "Artist 1", "uri/song1.mp3", true),
//            PlaylistDTO(2, "Song 2", "Artist 2", "uri/song2.mp3", true),
//            PlaylistDTO(3, "Song 3", "Artist 3", "uri/song3.mp3", true),
//            PlaylistDTO(4, "Song 4", "Artist 4", "uri/song4.mp3", true),
//            PlaylistDTO(5, "Song 5", "Artist 5", "uri/song5.mp3", true),
//            PlaylistDTO(6, "Song 6", "Artist 6", "uri/song6.mp3", true)
//        )
//    }

    private fun generateQuestions(albumSongs: List<TrackResponseModel>): List<Question> {
        return albumSongs.take(5).mapIndexed { index, track ->
            Question(
                question = "What is the title of song ${index + 1}?",
                correctAnswer = if (index % 2 == 0) "A" else "B",
                songUri = track.preview
            )
        }
    }


    private fun displayQuestion(index: Int) {
        val question = questions[index]
        binding.txtQuestion.text = question.question
    }

    private fun checkAnswer(question: Question, answer: String) {
        if (question.correctAnswer == answer) {
            Toast.makeText(requireContext(), "Correct!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun playMusic(uri: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(uri)
            mediaPlayer.prepare()
            mediaPlayer.start()
        } catch (e: Exception) {
            Log.e("QuizFragment", "Error playing music: ${e.message}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mediaPlayer.release()
    }
}


