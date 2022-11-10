package id.eureka.githubusers.users.presentation.users

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
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
                    is UserUIState.SearchUserSuccess -> adapter.submitData(state.data)
                }
            }
        }
    }

    private fun setRecyclerView() {
        with(binding.rvUsers) {
            layoutManager = LinearLayoutManager(this@UsersActivity)
            adapter = this@UsersActivity.adapter.withLoadStateHeaderAndFooter(
                footer = LoadingStateAdapter {
                    this@UsersActivity.adapter.retry()
                },
                header = LoadingStateAdapter {
                    this@UsersActivity.adapter.retry()
                }
            )
        }
    }

    override fun onResume() {
        super.onResume()

        viewModel.searchUser("")
    }
}