package com.example.historyvideokotlin.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.historyvideokotlin.databinding.ItemUserManagementBinding
import com.example.historyvideokotlin.model.User

class UserManagementAdapter(val list: List<User>, val listener: ItemListener) :
    RecyclerView.Adapter<UserManagementAdapter.ViewHolder>() {
    class ViewHolder(val binding: ItemUserManagementBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User, listener: ItemListener) {
            binding.user = user
            binding.ivEdit.setOnClickListener {
                listener.onEdit(user)
            }
            binding.ivDelete.setOnClickListener {
                listener.onDelete(user.user_id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ViewHolder(
        ItemUserManagementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(list[position], listener)
    }

    override fun getItemCount() = list.size

    interface ItemListener {
        fun onEdit(user: User)
        fun onDelete(userId: String)
    }
}
