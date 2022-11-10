package id.eureka.githubusers.users.presentation.detailuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.eureka.githubusers.R
import id.eureka.githubusers.core.util.DateUtil
import id.eureka.githubusers.databinding.ItemRepositoryBinding
import id.eureka.githubusers.users.presentation.model.Repository

class RepositoryAdapter :
    PagingDataAdapter<Repository, RepositoryAdapter.RepositoryViewHolder>(repositoryDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        return RepositoryViewHolder(
            ItemRepositoryBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_repository, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        val repository = getItem(position)
        repository?.let { holder.setRepository(repository) }
    }

    inner class RepositoryViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setRepository(repository: Repository) {
            binding.tvRepositoryTitle.text = repository.fullName
            binding.tvRepositoryDescription.text = repository.description
            binding.tvStarredRepositoryCount.text = repository.stargazersCount.toString()
            binding.tvLastUpdated.text = DateUtil.formatDate(repository.updatedAt)
        }
    }

    companion object {

        val repositoryDiffCallback = object : DiffUtil.ItemCallback<Repository>() {
            override fun areItemsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Repository, newItem: Repository): Boolean {
                return oldItem == newItem
            }
        }
    }
}