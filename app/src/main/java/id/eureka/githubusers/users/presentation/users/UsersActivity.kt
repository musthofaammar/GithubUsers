package id.eureka.githubusers.users.presentation.users

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isEmpty
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import id.eureka.githubusers.core.util.showShortSnackBar
import id.eureka.githubusers.databinding.ActivityUsersBinding
import id.eureka.githubusers.users.presentation.LoadingStateAdapter
import id.eureka.githubusers.users.presentation.model.UserUIState
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class UsersActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsersBinding
    private val viewModel: UserViewModel by viewModels()
    private val adapter by lazy { UserAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUsersBinding.inflate(layoutInflater)

        setContentView(binding.root)

        setRecyclerView()
        setObserver()
        setSearchBox()
    }

    private fun setSearchBox() {
        binding.searchBox.edSearchUser.addTextChangedListener(onTextChanged = { text, _, _, _ ->
            viewModel.searchUser(text.toString())
        })
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.userUiState.collectLatest { state ->
                when (state) {
                    UserUIState.Empty -> Unit
                    is UserUIState.Error -> showShortSnackBar(binding.root, state.message)
                    UserUIState.Loading -> Unit
                    is UserUIState.SearchUserSuccess -> {
                        adapter.submitData(state.data)

                        binding.rvUsers.scrollToPosition(0)
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        with(binding.rvUsers) {
            layoutManager = LinearLayoutManager(context)
            adapter =
                this@UsersActivity.adapter.withLoadStateHeaderAndFooter(footer = LoadingStateAdapter {
                    this@UsersActivity.adapter.retry()
                }, header = LoadingStateAdapter {
                    this@UsersActivity.adapter.retry()
                })
        }


        adapter.addLoadStateListener { loadState ->
            val refreshState = loadState.mediator?.refresh

            binding.tvEmpty.isVisible = binding.rvUsers.isEmpty()

            binding.progressBar.isVisible = refreshState is LoadState.Loading

            if(refreshState is LoadState.Error){
                showShortSnackBar(binding.root, refreshState.error.message ?: "Holaho")
            }
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.searchUser(binding.searchBox.edSearchUser.text.toString())
    }
}