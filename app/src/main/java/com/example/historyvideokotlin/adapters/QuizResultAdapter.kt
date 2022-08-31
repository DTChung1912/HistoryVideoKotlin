package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemQuizResultBinding

class QuizResultAdapter(val anwserList: List<String>, val context: Context) :
    RecyclerView.Adapter<QuizResultAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemQuizResultBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(anwser: String, position: Int) = with(binding) {
            val number : Int = position + 1;
            binding.tvNumber.text = "Câu " + number + ": "
            binding.tvAnswer.text = anwser
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemQuizResultBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(anwserList[position],position)
    }

    override fun getItemCount(): Int = anwserList.size

}