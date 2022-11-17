package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemPostManagementBinding
import com.example.historyvideokotlin.model.Post

class PostManagementAdapter(val list: List<Post>, val listener: ItemListener) :
    RecyclerView.Adapter<PostManagementAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemPostManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(post: Post, listener: ItemListener) {
            binding.post = post

            binding.ivEdit.setOnClickListener {
                listener.onEdit(post)
            }
            binding.ivDelete.setOnClickListener {
                listener.onDelete(post.post_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPostManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount() = list.size

    interface ItemListener {
        fun onEdit(post: Post)
        fun onDelete(postId: Int)
    }
}
