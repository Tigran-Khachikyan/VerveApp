package com.example.vervetaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vervetaskapp.R
import com.example.vervetaskapp.databinding.ListItemCommentBinding

class CommentAdapter : RecyclerView.Adapter<CommentAdapter.CommentHolder>() {

    private val differ: AsyncListDiffer<String> = AsyncListDiffer(this, CommentDiffUtil())

    fun submitList(items: List<String>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item_comment, parent, false)
        return CommentHolder(view)

    }

    override fun onBindViewHolder(holder: CommentHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class CommentHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemCommentBinding.bind(view)
        fun bind(comment: String) {
            binding.root.text = comment
        }
    }

    private class CommentDiffUtil : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String) = oldItem == newItem
        override fun areContentsTheSame(oldItem: String, newItem: String) = oldItem == newItem
    }
}
