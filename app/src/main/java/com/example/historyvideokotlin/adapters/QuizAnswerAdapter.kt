package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.ItemQuizAnswerBinding
import com.example.historyvideokotlin.model.Quiz

class QuizAnswerAdapter(val quizList: List<Quiz>, val context: Context , val choosedAnswerList: List<String>) : RecyclerView.Adapter<QuizAnswerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemQuizAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(quiz: Quiz, context: Context, number: Int, choosedAnswer: String) = with(binding){

            tvNumber.text = "Câu " + number + ":"
            tvQuiz.text = quiz.question
            if (!quiz.image.isNullOrEmpty()) {
                ivImageQuiz.visibility = VISIBLE
                Glide.with(context).load(quiz.image).into(ivImageQuiz)
            }
            val answerList = listOf(quiz.correct, quiz.incorrect_1, quiz.incorrect_2, quiz.incorrect_3 )
            val ramdomAnswerList : MutableList<String> = emptyList<String>().toMutableList()
            while (ramdomAnswerList.size < 4) {
                val i = answerList.random()
                if (!ramdomAnswerList.contains(i)) {
                    ramdomAnswerList.add(i)
                }
            }

            tvAnswerA.text = ramdomAnswerList[0]
            tvAnswerB.text = ramdomAnswerList[1]
            tvAnswerC.text = ramdomAnswerList[2]
            tvAnswerD.text = ramdomAnswerList[3]

            if (choosedAnswer.equals(quiz.correct)) {
                when (choosedAnswer) {
                    tvAnswerA.text -> {
                        setRightAnswer(tvAnswerA,context)
                    }
                    tvAnswerB.text -> {
                        setRightAnswer(tvAnswerB,context)
                    }
                    tvAnswerC.text -> {
                        setRightAnswer(tvAnswerC,context)
                    }
                    tvAnswerD.text -> {
                        setRightAnswer(tvAnswerD,context)
                    }
                }
            } else {
                when (choosedAnswer) {
                    tvAnswerA.text -> {
                        setWrongAnswerColor(tvAnswerA,context)
                    }
                    tvAnswerB.text -> {
                        setWrongAnswerColor(tvAnswerB,context)
                    }
                    tvAnswerC.text -> {
                        setWrongAnswerColor(tvAnswerC,context)
                    }
                    tvAnswerD.text -> {
                        setWrongAnswerColor(tvAnswerD,context)
                    }
                }

                when (quiz.correct) {
                    tvAnswerA.text -> {
                        setRightAnswer(tvAnswerA,context)
                    }
                    tvAnswerB.text -> {
                        setRightAnswer(tvAnswerB,context)
                    }
                    tvAnswerC.text -> {
                        setRightAnswer(tvAnswerC,context)
                    }
                    tvAnswerD.text -> {
                        setRightAnswer(tvAnswerD,context)
                    }
                }

            }
        }

        private fun setRightAnswer(textView: TextView, context: Context) {
            textView.setBackgroundResource(R.drawable.background_answer_choosed)
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        fun setWrongAnswerColor(textView: TextView,context: Context) {
            textView.setBackgroundResource(R.drawable.background_answer_wrong)
            textView.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        }
    }



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemQuizAnswerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(quizList[position],context, position + 1, choosedAnswerList[position])
    }

    override fun getItemCount(): Int = quizList.size
}