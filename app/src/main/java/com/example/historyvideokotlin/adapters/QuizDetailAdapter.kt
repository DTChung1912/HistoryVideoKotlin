package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemQuizDetailBinding
import com.example.historyvideokotlin.model.Quiz

class QuizDetailAdapter(val quizList: List<Quiz>, val context: Context ) : RecyclerView.Adapter<QuizDetailAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemQuizDetailBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz, context: Context) = with(binding){
            binding.tvQuiz.text = quiz.question
            if (!quiz.image.isNullOrEmpty()) {
                binding.ivImageQuiz.visibility = VISIBLE
                Glide.with(context).load(quiz.image).into(binding.ivImageQuiz)
            }
            val answerList = listOf(quiz.correct, quiz.incorrect_1, quiz.incorrect_2, quiz.incorrect_3 )
            val ramdomAnswerList : MutableList<String> = emptyList<String>().toMutableList()
            while (ramdomAnswerList.size < 4) {
                val i = answerList.random()
                if (!ramdomAnswerList.contains(i)) {
                    ramdomAnswerList.add(i)
                }
            }

            binding.tvAnswerA.text = ramdomAnswerList[0]
            binding.tvAnswerB.text = ramdomAnswerList[1]
            binding.tvAnswerC.text = ramdomAnswerList[2]
            binding.tvAnswerD.text = ramdomAnswerList[3]
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemQuizDetailBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quizList[position],context)
    }

    override fun getItemCount(): Int = if (quizList.size == 1) 1 else Integer.MAX_VALUE
}