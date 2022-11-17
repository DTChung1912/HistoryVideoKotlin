package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemPostChildrenBinding
import com.example.historyvideokotlin.model.DownloadPost

class DownloadPostAdapter(
    val postList: List<DownloadPost>,
    val context: Context,
    val downloadPostListListener: DownloadPostListListener
) : RecyclerView.Adapter<DownloadPostAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPostChildrenBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: DownloadPost,
            context: Context,
            downloadPostListListener: DownloadPostListListener
        ) = with(binding) {
            binding.run {

                ivMore.setOnClickListener {
                    downloadPostListListener.onMore()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPostChildrenBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position], context, downloadPostListListener)
    }

    override fun getItemCount() = postList.size


}

interface DownloadPostListListener {
    fun onItemClick(post: DownloadPost)
    fun onMore()
}