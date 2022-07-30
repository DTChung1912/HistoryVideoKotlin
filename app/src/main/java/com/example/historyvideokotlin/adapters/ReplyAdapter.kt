package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemReplyBinding
import com.example.historyvideokotlin.model.Reply

class ReplyAdapter(
    val replyList: List<Reply>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ReplyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(reply: Reply, context: Context, onItemClickListener: OnItemClickListener) = with(binding){
            if (!reply.user_image.isNullOrEmpty()) {
                Glide.with(context).load(reply.user_image).into(civUserAvatar)
            }
            tvUserName.text = reply.user_name
            tvContent.text = reply.content
            tvLikeCount.text = reply.like_count
            tvDislikeCount.text = reply.dislike_count
            itemReply.setOnClickListener {
                onItemClickListener.onItemClick()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReplyAdapter.ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(replyList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = replyList.size

    interface OnItemClickListener {
        fun onItemClick()
    }

}