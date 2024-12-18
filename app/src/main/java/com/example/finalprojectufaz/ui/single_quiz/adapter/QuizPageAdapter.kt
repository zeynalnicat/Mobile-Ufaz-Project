package com.example.finalprojectufaz.ui.single_quiz.adapter

import android.os.Handler
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.ItemQuestionPageBinding
import com.example.finalprojectufaz.domain.mediaplayer.MusicPlayer
import com.example.finalprojectufaz.domain.quiz.QuizQuestionsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuizPagerAdapter(
    private val questionModels: QuizQuestionsModel,
    private val actionCallBac: ActionCallBac
) : RecyclerView.Adapter<QuizPagerAdapter.QuestionViewHolder>() {

    interface ActionCallBac {
        fun nav()
        fun next()
        fun back()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val view = ItemQuestionPageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return QuestionViewHolder(view)
    }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        holder.bind(questionModels, position)
    }

    override fun getItemCount(): Int = questionModels.questions.size

    inner class QuestionViewHolder(private val binding: ItemQuestionPageBinding) : RecyclerView.ViewHolder(binding.root),AnswersAdapter.AnswerActionCallback {
        private var correctAnswer:String? = null
        private var currentTrackUri: String? = null
        private val handler = Handler()
        private var updateProgressBarRunnable: Runnable? = null

        fun bind(question: QuizQuestionsModel, position: Int) {
            val uri = question.previews[position]
            var isPlaying = false
            correctAnswer = question.answers[position]

            if(position==0){
                binding.btnPrevious.text = "Cancel"
                binding.btnPrevious.setOnClickListener {
                    actionCallBac.nav()
                    QuizQuestionsModel.wrongAnswers=0
                    QuizQuestionsModel.correctAnswers=0
                    MusicPlayer.getInstance().stopMusic()
                }
            }else{
                binding.btnPrevious.setOnClickListener {
                     actionCallBac.back()
                    MusicPlayer.getInstance().stopMusic()
                }
            }

            if(position==questionModels.questions.size-1){
                binding.btnNext2.text = "Finish"
            }else{
                binding.btnNext2.setOnClickListener {
                    actionCallBac.next()
                    MusicPlayer.getInstance().stopMusic()
                }
            }

            binding.btnPlay.setOnClickListener {
                MusicPlayer.getInstance().stopMusic()
                isPlaying = !isPlaying

                if (isPlaying) {
                    binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.icon_pause))

                    if (uri != currentTrackUri) {
                        currentTrackUri = uri
                        CoroutineScope(Dispatchers.IO).launch {
                            MusicPlayer.getInstance().startMusic(itemView.context.applicationContext, uri)
                        }
                    }

                    startUpdatingProgressBar()
                } else {
                    binding.btnPlay.setImageDrawable(ContextCompat.getDrawable(itemView.context, R.drawable.icon_play))
                    CoroutineScope(Dispatchers.IO).launch {
                        MusicPlayer.getInstance().pauseMusic()
                    }

                    stopUpdatingProgressBar()
                }
            }

            binding.progressPreview.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        CoroutineScope(Dispatchers.IO).launch {
                            MusicPlayer.getInstance().seekTo(progress)
                        }
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {}

                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            })

            binding.txtQuestion.text = question.questions[position]
            binding.txtQuestionTitle.text = "Question ${position+1}/${questionModels.questions.size}"
            val adapter = AnswersAdapter(this)
            adapter.submitList(question.options[position])
            binding.answersRecycler.layoutManager = GridLayoutManager(itemView.context,1)
            binding.answersRecycler.adapter = adapter
            }

        private fun startUpdatingProgressBar() {
            updateProgressBarRunnable = object : Runnable {
                override fun run() {
                    val musicPlayer = MusicPlayer.getInstance()

                    if (musicPlayer.isPlaying) {
                        val currentPosition = musicPlayer.getCurrentPosition()
                        val maxDuration = musicPlayer.getDuration()

                        if (maxDuration > 0) {
                            val progress = (currentPosition * 100) / maxDuration
                            binding.progressPreview.progress = progress
                        } else {
                            binding.progressPreview.progress = 0
                        }
                    } else {
                        binding.progressPreview.progress = 0
                    }

                    handler.postDelayed(this, 1000)
                }
            }
            handler.post(updateProgressBarRunnable!!)
        }



        private fun stopUpdatingProgressBar() {
            handler.removeCallbacks(updateProgressBarRunnable!!)
        }

        override fun clickAnswer(answer: String):Boolean {
            return answer==correctAnswer
        }

        override fun nav() {
            MusicPlayer.getInstance().stopMusic()
             actionCallBac.next()
        }
    }


}
