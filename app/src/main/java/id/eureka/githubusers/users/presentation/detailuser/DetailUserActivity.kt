package id.eureka.githubusers.users.presentation.detailuser

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import id.eureka.githubusers.core.util.showImageCircleUrl
import id.eureka.githubusers.core.util.showShortSnackBar
import id.eureka.githubusers.databinding.ActivityDetailUserBinding
import id.eureka.githubusers.users.presentation.model.DetailUserUIState
import id.eureka.githubusers.users.presentation.model.User
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserFromIntent()
        setObserver()
    }

    private fun setObserver() {
        lifecycleScope.launchWhenStarted {
            viewModel.detailUserState.collectLatest { state ->
                when (state) {
                    DetailUserUIState.Empty -> Unit
                    is DetailUserUIState.Error -> showShortSnackBar(binding.root, state.message)
                    is DetailUserUIState.GetUserDetailSuccess -> setUser(state.data)
                    DetailUserUIState.Loading -> Unit
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUser(data: User) {
        binding.tvUserRealName.text = data.name.ifEmpty { "Doe" }
        binding.tvUsername.text = "@${data.login}"
        binding.tvBio.text = data.bio
        showImageCircleUrl(this, binding.ivUser, data.avatarUrl)
    }

    private fun getUserFromIntent() {
        val extras = intent.extras
        extras?.let {

            val user = it.getParcelable(USER_DATA) as User?

            user?.let { value ->
                viewModel.getUserDetail(value.login, value.id)
            }
        }
    }

    companion object {
        const val USER_DATA = "USER_DATA"
    }
}