package id.eureka.githubusers.users.presentation.detailuser

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import id.eureka.githubusers.databinding.ActivityDetailUserBinding
import id.eureka.githubusers.users.presentation.model.User

class DetailUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private val viewModel: DetailUserViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getUserFromIntent()
    }

    private fun getUserFromIntent() {
        val extras = intent.extras
        extras?.let {

            val user = it.getParcelable(USER_DATA) as User?

            if (!user?.login.isNullOrEmpty() && user?.id != null) {
                viewModel.getUserDetail(user.login, user.id)
            }
        }
    }

    companion object {
        const val USER_DATA = "USER_DATA"
    }
}