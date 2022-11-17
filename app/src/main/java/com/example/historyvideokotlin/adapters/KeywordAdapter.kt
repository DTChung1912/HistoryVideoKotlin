package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemKeywordBinding
import com.example.historyvideokotlin.model.Keyword

class KeywordAdapter(val keywordList: List<Keyword>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<KeywordAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemKeywordBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Keyword, onItemClickListener: OnItemClickListener) = with(binding) {
            binding.run {

                keyword = item.content

                ivPaste.setOnClickListener {
                    onItemClickListener.onPaste(item)
                }

                tvKeyword.setOnClickListener {
                    onItemClickListener.onKeyword(item)
                }

                ivDelete.setOnClickListener {
                    onItemClickListener.onDelete(item)
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
        fun onKeyword(keyword: Keyword)
        fun onDelete(keyword: Keyword)
        fun onPaste(keyword: Keyword)
    }
}