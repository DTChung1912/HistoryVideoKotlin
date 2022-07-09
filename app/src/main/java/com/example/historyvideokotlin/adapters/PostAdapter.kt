package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemFeedBinding

class PostAdapter : RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemFeedBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemFeedBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 5
}