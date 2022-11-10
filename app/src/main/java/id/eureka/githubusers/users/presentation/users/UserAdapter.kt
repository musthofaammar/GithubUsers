package id.eureka.githubusers.users.presentation.users

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import id.eureka.githubusers.R
import id.eureka.githubusers.core.util.showImageCircleUrl
import id.eureka.githubusers.databinding.ItemUserBinding
import id.eureka.githubusers.users.presentation.detailuser.DetailUserActivity
import id.eureka.githubusers.users.presentation.model.User

class UserAdapter : PagingDataAdapter<User, UserAdapter.UserViewHolder>(userDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.bind(
                LayoutInflater.from(parent.context).inflate(
                    R.layout.item_user, parent, false
                )
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        user?.let { holder.setUser(it) }
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setUser(user: User) {
            binding.tvUsername.text = user.name.ifEmpty { user.login }
            showImageCircleUrl(binding.root.context, binding.ivUser, user.avatarUrl)

            binding.root.setOnClickListener {
                val optionsCompat: ActivityOptionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation(
                        itemView.context as Activity,
                        Pair(binding.tvUsername, "userName"),
                        Pair(binding.ivUser, "userImage")
                    )

                val intent = Intent(itemView.context, DetailUserActivity::class.java)
                intent.putExtra(DetailUserActivity.USER_DATA, user)

                it.context.startActivity(intent, optionsCompat.toBundle())
            }
        }
    }

    companion object {

        val userDiffCallback = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }
        }
    }
}