package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemQuizManagementBinding
import com.example.historyvideokotlin.model.Quiz

class QuizManagementAdapter(val list: List<Quiz>, val listener: ItemListener) :
    RecyclerView.Adapter<QuizManagementAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemQuizManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private var isShow = true

        fun bind(quiz: Quiz, listener: ItemListener) {
            binding.quiz = quiz

            binding.item.setOnClickListener {
                binding.llAnswer.visibility = if (isShow) VISIBLE else GONE
                isShow = !isShow
            }

            binding.ivEdit.setOnClickListener {
                listener.onEdit(quiz)
            }
            binding.ivDelete.setOnClickListener {
                listener.onDelete(quiz.quiz_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemQuizManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount() = list.size

    interface ItemListener {
        fun onEdit(quiz: Quiz)
        fun onDelete(quizId: Int)
    }
}
