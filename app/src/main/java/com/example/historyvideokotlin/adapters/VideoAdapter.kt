package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemVideoBinding

class VideoAdapter : RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemVideoBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder (
        ItemVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 10
}