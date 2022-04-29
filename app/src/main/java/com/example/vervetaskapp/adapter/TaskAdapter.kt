package com.example.vervetaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vervetaskapp.R
import com.example.vervetaskapp.databinding.ListItemProjectBinding
import com.example.vervetaskapp.databinding.ListItemTaskBinding
import com.example.vervetaskapp.models.Project
import com.example.vervetaskapp.models.Task
import com.example.vervetaskapp.utils.visibleOrBone
import java.text.DateFormat


class TaskAdapter(private val onSelect: (Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.Holder>() {

    private val differ: AsyncListDiffer<Task> = AsyncListDiffer(this, TaskDiffUtil())

    fun submitList(items: List<Task>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_task, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemTaskBinding.bind(view)
        fun bind(task: Task) {
            binding.run {
                tvTitle.text = task.title
                tvDescription.run {
                    visibleOrBone(!task.description.isNullOrEmpty())
                    text = task.description
                }
                tvCreatedDate.text = DateFormat.getDateTimeInstance().format(task.creationDate)
                root.setOnClickListener {
                    onSelect.invoke(differ.currentList[layoutPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private class TaskDiffUtil : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(oldItem: Task, newItem: Task) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Task, newItem: Task) = oldItem == newItem
    }
}
