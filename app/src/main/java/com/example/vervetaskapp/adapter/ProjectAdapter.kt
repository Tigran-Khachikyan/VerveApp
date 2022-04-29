package com.example.vervetaskapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.vervetaskapp.R
import com.example.vervetaskapp.databinding.ListItemProjectBinding
import com.example.vervetaskapp.models.Project

class ProjectAdapter(private val onSelect: (Project) -> Unit) :
    RecyclerView.Adapter<ProjectAdapter.Holder>() {

    private val differ: AsyncListDiffer<Project> = AsyncListDiffer(this, ProjectsDiffUtil())

    fun submitList(items: List<Project>) {
        differ.submitList(items)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_project, parent, false)
        return Holder(v)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    inner class Holder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ListItemProjectBinding.bind(view)
        fun bind(project: Project) {
            binding.root.run {
                text = project.title
                setOnClickListener {
                    onSelect.invoke(differ.currentList[layoutPosition])
                }
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    private class ProjectsDiffUtil : DiffUtil.ItemCallback<Project>() {
        override fun areItemsTheSame(oldItem: Project, newItem: Project) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Project, newItem: Project) = oldItem == newItem
    }
}
