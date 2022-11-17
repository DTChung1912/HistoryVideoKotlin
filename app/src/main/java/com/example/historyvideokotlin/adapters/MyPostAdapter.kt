package com.example.historyvideokotlin.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.historyvideokotlin.R
import com.example.historyvideokotlin.databinding.ItemPostChildrenBinding
import com.example.historyvideokotlin.model.MyPostResponse
import com.example.historyvideokotlin.model.MyPostStatus
import com.example.historyvideokotlin.model.Post

class MyPostAdapter(
    val myPostResponse: MyPostResponse,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<MyPostAdapter.ViewHolder>() {

    class ViewHolder(val binding: ItemPostChildrenBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: Post,
            myPostStatus: MyPostStatus,
            context: Context,
            onItemClickListener: OnItemClickListener
        ) =
            with(binding) {
                binding.run {
                    if (post.image.isNullOrEmpty()) {
                        ivPostList.setImageResource(R.drawable.ic_avatar)
                    } else {
                        Glide.with(context).load(post.image).into(ivPostList)
                    }
                    tvTitle.text = post.title
                    tvReadCount.text = post.read_count.toString()
                    tvRateCount.text = post.rate_count.toString()

                    itemPostChildren.setOnClickListener {
                        onItemClickListener.onMyPostClick(post)
                    }

                    ivMore.setOnClickListener {
                        onItemClickListener.onMyPostMore(myPostStatus.my_post_id)
                    }
                }
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemPostChildrenBinding.inflate(LayoutInflater.from(context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(
            myPostResponse.postList[position],
            myPostResponse.myPostStatusList[position],
            context,
            onItemClickListener
        )
    }

    override fun getItemCount() = myPostResponse.postList.size

    interface OnItemClickListener {
        fun onMyPostClick(post: Post)
        fun onMyPostMore(myPostId: Int)
    }
}
