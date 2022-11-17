package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemCommentBinding
import com.example.historyvideokotlin.model.Comment

class CommentAdapter(
    val commentList: List<Comment>,
    val isLikeList: List<Int>,
    val context: Context,
    val itemListener: ItemListener
) :
    RecyclerView.Adapter<CommentAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemCommentBinding) : RecyclerView.ViewHolder(binding.root) {
        private var isLikeData = 0
        private var commentData = Comment()

        private fun setItem() {
            binding.comment = commentData
            when (isLikeData) {
                0 -> {
                    binding.isLike = false
                    binding.isDislike = false
                }
                1 -> {
                    binding.isLike = true
                    binding.isDislike = false
                }
                2 -> {
                    binding.isLike = false
                    binding.isDislike = true
                }
            }
        }

        fun bind(
            comment: Comment,
            isLike: Int,
            itemListener: ItemListener
        ) {
            commentData = comment
            isLikeData = isLike

            setItem()
            binding.itemComment.setOnClickListener {
                itemListener.onReply(commentData, isLikeData)
            }

            binding.ivLike.setOnClickListener {
                if (isLikeData == 2) {
                    commentData.dislike_count--
                }
                isLikeData = 1
                commentData.like_count++
                setItem()
                itemListener.onLiked(commentData.comment_id)
            }
            binding.ivLiked.setOnClickListener {
                isLikeData = 0
                commentData.like_count--
                setItem()
                itemListener.onLikeCancel(commentData.comment_id)
            }
            binding.ivDislike.setOnClickListener {
                if (isLikeData == 1) {
                    commentData.like_count--
                }
                isLikeData = 2
                commentData.dislike_count++
                setItem()
                itemListener.onDisliked(commentData.comment_id)
            }
            binding.ivDisliked.setOnClickListener {
                isLikeData = 0
                commentData.dislike_count--
                setItem()
                itemListener.onDislikeCancel(commentData.comment_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(commentList[position], isLikeList[position], itemListener)
    }

    override fun getItemCount(): Int = commentList.size

    interface ItemListener {
        fun onReply(comment: Comment, isLike: Int)
        fun onLiked(commentId: Int)
        fun onLikeCancel(commentId: Int)
        fun onDisliked(commentId: Int)
        fun onDislikeCancel(commentId: Int)
    }
}
