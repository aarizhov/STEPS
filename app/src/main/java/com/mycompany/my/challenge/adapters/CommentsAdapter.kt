package com.mycompany.my.challenge.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mycompany.my.challenge.R
import com.mycompany.my.challenge.databinding.RowCommentBinding
import com.mycompany.my.challenge.models.Comment

class CommentsAdapter: PagedListAdapter<Comment, CommentsAdapter.CommentsViewHolder>(
    commentComparator) {

    companion object{
        val commentComparator = object : DiffUtil.ItemCallback<Comment>(){
            override fun areItemsTheSame(oldItem: Comment, newItem: Comment): Boolean {
                return newItem.body == oldItem.body && newItem.email == oldItem.email && newItem.name == oldItem.name && newItem.postId == oldItem.postId
            }
            override fun areContentsTheSame(oldItem: Comment, newItem: Comment): Boolean = oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentsViewHolder {
        return CommentsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.row_comment, parent, false))
    }

    override fun onBindViewHolder(holder: CommentsViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class CommentsViewHolder(itemBinding: RowCommentBinding): RecyclerView.ViewHolder(itemBinding.root) {
        private val binding = itemBinding
        fun bind(item: Comment){
            binding.comment = item
        }
    }
}