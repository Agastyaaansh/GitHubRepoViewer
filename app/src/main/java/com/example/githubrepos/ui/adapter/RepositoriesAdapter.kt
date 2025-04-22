package com.example.githubrepos.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.githubrepos.data.model.Repository
import com.example.githubrepos.databinding.ItemRepositoryBinding

class RepositoriesAdapter : ListAdapter<Repository, RepositoriesAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(val binding: ItemRepositoryBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemRepositoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val repo = getItem(position)
        holder.binding.apply {
            repoName.text = repo.name
            repoDescription.text = repo.description ?: "No description"
            repoLanguage.text = repo.language ?: "Unknown"
            starsCount.text = repo.stargazers_count.toString()
            forksCount.text = repo.forks_count.toString()
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Repository>() {
        override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
            return oldItem == newItem
        }
    }
}