package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemKeywordBinding

class KeywordAdapter(val keywordList: List<String>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(keyword: String, onItemClickListener: OnItemClickListener) = with(binding) {
            binding.run {

                tvKeyword.text = keyword

                itemKeyword.setOnClickListener {
                    onItemClickListener.onKeyword(tvKeyword.text.toString())
                }
                ivDelete.setOnClickListener {
                    onItemClickListener.onDelete(tvKeyword.text.toString())
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemKeywordBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(keywordList[position], onItemClickListener)
    }

    override fun getItemCount(): Int = keywordList.size

    interface OnItemClickListener {
        fun onKeyword(keyword: String)
        fun onDelete(keyword: String)
    }
}