package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemReplyBinding
import com.example.historyvideokotlin.model.Reply

class ReplyAdapter(
    val replyList: List<Reply>,
    val isLikeList: List<Int>,
    val context: Context,
    val itemListener: ItemListener
) :
    RecyclerView.Adapter<ReplyAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemReplyBinding) : RecyclerView.ViewHolder(binding.root) {

        private var isLikeData = 0
        private var replyData = Reply()

        private fun setItem() {
            binding.reply = replyData
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

        fun bind(reply: Reply, isLike: Int, itemListener: ItemListener) {
            replyData = reply
            isLikeData = isLike
            setItem()

            binding.itemReply.setOnClickListener {
                itemListener.onReply(reply.user_name)
            }
            binding.ivLike.setOnClickListener {
                if (isLikeData == 2) {
                    replyData.dislike_count--
                }
                isLikeData = 1
                replyData.like_count++
                setItem()
                itemListener.onReplyLike(replyData.comment_id)
            }
            binding.ivLiked.setOnClickListener {
                isLikeData = 0
                replyData.like_count--
                setItem()
                itemListener.onReplyLikeCancel(replyData.comment_id)
            }
            binding.ivDislike.setOnClickListener {
                if (isLikeData == 1) {
                    replyData.like_count--
                }
                isLikeData = 2
                replyData.dislike_count++
                setItem()
                itemListener.onReplyDislike(replyData.comment_id)
            }
            binding.ivDisliked.setOnClickListener {
                isLikeData = 0
                replyData.dislike_count--
                setItem()
                itemListener.onReplyDislikeCancel(replyData.comment_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemReplyBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(replyList[position], isLikeList[position], itemListener)
    }

    override fun getItemCount(): Int = replyList.size

    interface ItemListener {
        fun onReply(userName: String)
        fun onReplyLike(replyId: Int)
        fun onReplyDislike(replyId: Int)
        fun onReplyLikeCancel(replyId: Int)
        fun onReplyDislikeCancel(replyId: Int)
    }
}
