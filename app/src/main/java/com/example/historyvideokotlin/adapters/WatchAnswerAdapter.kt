package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.iterator
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.ItemQuizAnswerBinding
import com.example.historyvideokotlin.model.AnswerModel
import com.example.historyvideokotlin.model.Quiz
import com.example.historyvideokotlin.model.SelectAnswer
import com.example.historyvideokotlin.utils.MyLog

class WatchAnswerAdapter(
    val quizList: List<Quiz>,
    val context: Context,
    val selectList: List<SelectAnswer>,
    val answerList: List<AnswerModel>
) : RecyclerView.Adapter<WatchAnswerAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemQuizAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            quiz: Quiz,
            context: Context,
            number: Int,
            selectAnswer: SelectAnswer,
            answer: AnswerModel
        ) =
            with(binding) {
                this.number = number
                this.quiz = quiz
                this.answer = answer

                for (i in llAnswer.children) {
                    if ((i as TextView).text.equals(quiz.correct)) {
                        setRightAnswer(i, context)
                    }
                }
            }

        private fun setRightAnswer(textView: TextView, context: Context) {
            textView.setBackgroundResource(R.drawable.background_answer_choosed)
            textView.setTextColor(ContextCompat.getColor(context, R.color.white))
        }

        private fun setWrongAnswerColor(textView: TextView, context: Context) {
            textView.setBackgroundResource(R.drawable.background_answer_wrong)
            textView.setTextColor(ContextCompat.getColor(context, R.color.yellow))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemQuizAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        MyLog.e("quizList",quizList.toString())
        MyLog.e("selectList",selectList.toString())
        MyLog.e("answerList",answerList.toString())

        holder.bind(
            quizList[position],
            context,
            position + 1,
            selectList[position],
            answerList[position]
        )
    }

    override fun getItemCount(): Int = quizList.size
}
