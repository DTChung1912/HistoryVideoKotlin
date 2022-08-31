package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.databinding.ItemCommentBinding
import com.example.historyvideokotlin.model.Comment

class CommentAdapter(
    val commentList: List<Comment>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            comment: Comment,
            context: Context,
            onItemClickListener: OnItemClickListener
        ) = with(binding) {
            if (comment.user_image.isNullOrEmpty()) {
                Glide.with(context).load(comment.user_image).into(civUserAvatar)
            }
            tvUserName.text = comment.user_name
            tvContent.text = comment.content
            tvDateSubmitted.text = comment.date_submitted
            tvLikeCount.text = comment.like_count
            tvDislikeCount.text = comment.dislike_count
            tvReplyCount.text = comment.reply_count
            itemComment.setOnClickListener {
                onItemClickListener.onReply(comment)
            }
            ivLike.setOnClickListener {
                onItemClickListener.onLiked(comment.comment_id)
            }
            ivDislike.setOnClickListener {
                onItemClickListener.onDisliked(comment.comment_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position], context, onItemClickListener)
    }

    override fun getItemCount(): Int = commentList.size

    interface OnItemClickListener {
        fun onReply(comment: Comment)
        fun onLiked(commentId: String)
        fun onDisliked(commentId: String)
    }
}