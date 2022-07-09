package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemNextVideoBinding

class NextVideoAdapter : RecyclerView.Adapter<NextVideoAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemNextVideoBinding) : RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemNextVideoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NextVideoAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 10
}