package id.eureka.githubusers.users.presentation.detailuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import id.eureka.githubusers.R

class DetailUserActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
    }

    companion object {
        const val USER_DATA = "USER_DATA"
    }
}