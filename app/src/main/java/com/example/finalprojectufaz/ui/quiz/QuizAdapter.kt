package com.example.finalprojectufaz.ui.quiz


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.databinding.ItemQuizListBinding
import com.example.finalprojectufaz.domain.quiz.QuizDTO

class QuizAdapter:RecyclerView.Adapter<QuizAdapter.ViewHolder>() {
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
            binding.txtPlaylistName.text = quiz.playlistName
            if(quiz.isCompleted==null || !quiz.isCompleted){
                binding.txtCompletion.visibility = View.GONE
            }else{
                binding.txtCompletion.text = "Completed"
            }



        }
    }

    fun submitList(list:List<QuizDTO>){
        diffUtil.submitList(list)
    }
}