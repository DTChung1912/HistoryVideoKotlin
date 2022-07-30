package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemPostListBinding
import com.example.historyvideokotlin.model.PostListData

class PostListAdapter(
    val postList: List<PostListData>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            postListData: PostListData,
            context: Context,
            onItemClickListener: OnItemClickListener
        ) = with(binding) {
            if (postListData.image != null && !postListData.image.isEmpty()) {
                Glide.with(context).load(postListData.image).into(binding.ivPostList)
            }
            binding.tvPostListTitle.text = postListData.title
            binding.tvPostListYear.text = postListData.year
            binding.tvPostListDescription.text = ""
            binding.itemPostList.setOnClickListener {
                onItemClickListener.onItemClick(postListData)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPostListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(postList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = postList.size

    interface OnItemClickListener {
        fun onItemClick(postListData: PostListData)
    }
}