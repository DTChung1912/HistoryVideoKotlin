package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemPostListBinding
import com.example.historyvideokotlin.model.Post

class PostListAdapter(
    val postList: List<Post>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<PostListAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPostListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: Post,
            context: Context,
            onItemClickListener: OnItemClickListener
        ) = with(binding) {
            binding.run {
                if (post.image != null && !post.image.isNullOrEmpty()) {
                    Glide.with(context).load(post.image).into(ivPostList)
                }
                tvPostListTitle.text = post.title
                tvReadCount.text = post.read_count + " lượt đọc"
                tvDownloadCount.text = post.download_count + " lượt tải xuống"
//            binding.tvPostListYear.text = post.timeline
//                tvPostListDescription.text = post.description
                itemPostList.setOnClickListener {
                    onItemClickListener.onItemClick(post)
                }
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
        fun onItemClick(postListData: Post)
    }
}