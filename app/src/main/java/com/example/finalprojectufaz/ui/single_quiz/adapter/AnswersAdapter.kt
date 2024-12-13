package com.example.finalprojectufaz.ui.single_quiz.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil.ItemCallback
import androidx.recyclerview.widget.RecyclerView
import com.example.finalprojectufaz.R
import com.example.finalprojectufaz.databinding.ItemQuizAnswersBinding
import com.example.finalprojectufaz.domain.quiz.QuizQuestionsModel

class AnswersAdapter(private val actionCallback: AnswerActionCallback):RecyclerView.Adapter<AnswersAdapter.AnswerViewHolder>() {

    interface AnswerActionCallback{
        fun clickAnswer(answer:String):Boolean
        fun nav()
    }

    private val callBack = object:ItemCallback<String>(){
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem===newItem
        }

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
            return oldItem==newItem
        }
    }

    private val diffUtil = AsyncListDiffer(this,callBack)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val view = ItemQuizAnswersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return AnswerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return diffUtil.currentList.size
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        return holder.bind(diffUtil.currentList[position])
    }


    inner class AnswerViewHolder(private val binding:ItemQuizAnswersBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(item:String){
             binding.txtAnswer.text = item

             itemView.setOnClickListener {
                 binding.answerContainer.animate()
                     .scaleX(0.8f)
                     .scaleY(0.8f)
                     .setDuration(300)
                     .withEndAction {
                         binding.answerContainer.animate()
                             .scaleX(1f)
                             .scaleY(1f)
                             .setDuration(300)
                             .start()
                     }
                     .start()

                 if(actionCallback.clickAnswer(item)){
                     QuizQuestionsModel.correctAnswers++
                     binding.answerContainer.setBackgroundDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.bg_selected_correct_answer))
                     binding.root.postDelayed({
                         actionCallback.nav()
                     }, 2000)
                 }else{
                     QuizQuestionsModel.wrongAnswers++
                     binding.answerContainer.setBackgroundDrawable(ContextCompat.getDrawable(itemView.context,R.drawable.bg_selected_wrong_answer))
                     binding.root.postDelayed({
                         actionCallback.nav()
                     }, 2000)
                 }
             }


        }
    }

    fun submitList(answers:List<String>){
        diffUtil.submitList(answers)
    }
}