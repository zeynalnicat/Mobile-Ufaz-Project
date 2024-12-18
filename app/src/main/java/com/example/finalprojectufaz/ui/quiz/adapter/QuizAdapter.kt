package com.example.finalprojectufaz.ui.quiz.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.ItemQuizListBinding
import com.example.finalprojectufaz.domain.quiz.QuizDTO

class QuizAdapter(private val action:ActionCallBack):RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    interface ActionCallBack{
        fun nav(id:Int)
        fun handleBottomSheet(id:Int)
    }
    private val callBack = object: DiffUtil.ItemCallback<QuizDTO>(){
        override fun areItemsTheSame(oldItem: QuizDTO, newItem: QuizDTO): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: QuizDTO, newItem: QuizDTO): Boolean {
            return oldItem==newItem
        }
    }

    private val diffUtil = AsyncListDiffer(this,callBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = ItemQuizListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        return holder.bind(diffUtil.currentList[position])
    }

    inner class ViewHolder(private val binding:ItemQuizListBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(quiz: QuizDTO){
            binding.txtQuizName.text = "Quiz #${layoutPosition}"
            binding.txtPlaylistName.text = quiz.name
            binding.txtCount.text = "${quiz.numberOfQuestions} Questions"
            if(quiz.isCompleted==null || !quiz.isCompleted){
                binding.txtCompletion.visibility = View.GONE
                binding.txtWrong.visibility = View.GONE
                binding.txtNumberCorrect.visibility= View.GONE
                binding.txtNumberWrong.visibility = View.GONE
                binding.txtCorrect.visibility = View.GONE
            }else{
                binding.txtCompletion.text = "Completed"
                binding.txtCompletion.setTextColor(ContextCompat.getColor(binding.root.context, R.color.spotifyGreen))
                binding.txtNumberWrong.text = quiz.wrongAnswers.toString()
                binding.txtNumberCorrect.text = quiz.correctAnswers.toString()
            }

            itemView.setOnClickListener {
                action.nav(quiz.id)
            }

            itemView.setOnLongClickListener {
                itemView.animate()
                    .scaleX(0.8f)
                    .scaleY(0.8f)
                    .setDuration(200)
                    .withEndAction {
                        itemView.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(200)
                            .start()
                    }.start()
                action.handleBottomSheet(quiz.id)
                true
            }

        }
    }

    fun submitList(list:List<QuizDTO>){
        diffUtil.submitList(list)
    }
}