package com.enraher.githublist.ui.repos

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.enraher.githublist.databinding.CellRepoBinding


data class RepoClickListener(val onClickListener: (repo: RepoModels.Repo) -> Unit)

class ReposPagingAdapter(private val clickListener: RepoClickListener):
    PagedListAdapter<RepoModels.Repo, ReposPagingAdapter.RepoViewHolder>(DiffUtilCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoViewHolder {
        val itemBinding: CellRepoBinding =
            CellRepoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepoViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: RepoViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it, clickListener.onClickListener) }
    }

    inner class RepoViewHolder(private val binding: CellRepoBinding)
        : RecyclerView.ViewHolder(binding.root) {
        fun bind(repo: RepoModels.Repo, clickListener: (RepoModels.Repo) -> Unit) {
            binding.repo = repo
            binding.root.setOnClickListener{clickListener(repo)}
            binding.executePendingBindings()
        }
    }
}

class DiffUtilCallBack : DiffUtil.ItemCallback<RepoModels.Repo>() {
    override fun areItemsTheSame(
        oldItem: RepoModels.Repo,
        newItem: RepoModels.Repo) = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: RepoModels.Repo, newItem: RepoModels.Repo): Boolean {
        return oldItem.id == newItem.id
                && oldItem.name == newItem.name
                && oldItem.fork == newItem.fork
                && oldItem.description == newItem.description
                && oldItem.ownerAvatar == newItem.ownerAvatar
                && oldItem.url == newItem.url
                && oldItem.ownerLogin == newItem.ownerLogin
                && oldItem.fullName == newItem.fullName
                && oldItem.watchers == newItem.watchers
                && oldItem.forks == newItem.forks
                && oldItem.openIssues == newItem.openIssues
    }
}