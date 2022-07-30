package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemQuizBinding
import com.example.historyvideokotlin.model.PostTheme

class QuizAdapter(
    val themeList: List<PostTheme>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<QuizAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemQuizBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            theme: PostTheme,
            context: Context,
            onItemClickListener: OnItemClickListener
        ) = with(binding) {

            binding.tvTheme.text = theme.theme_name
            binding.itemQuiz.setOnClickListener {
                onItemClickListener.onItemClick(theme)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(themeList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = themeList.size

    interface OnItemClickListener {
        fun onItemClick(postTheme: PostTheme)
    }
}