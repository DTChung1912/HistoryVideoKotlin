package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemAnswerBinding
import com.example.historyvideokotlin.model.SelectAnswer

class AnswerListAdapter(
    val answerList: List<SelectAnswer>,
    val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<AnswerListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemAnswerBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: SelectAnswer, itemClickListener: ItemClickListener) = with(binding) {
            number = item.number
            answer = item.title
            content = item.content
            itemAnswer.setOnClickListener {
                itemClickListener.onClick(item.number!! - 1)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemAnswerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(answerList[position], itemClickListener)
    }

    override fun getItemCount() = answerList.size

    interface ItemClickListener {
        fun onClick(position: Int)
    }
}