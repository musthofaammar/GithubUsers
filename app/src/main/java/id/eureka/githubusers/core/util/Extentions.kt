package id.eureka.githubusers.core.util

import android.content.Context
import android.view.View
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import id.eureka.githubusers.R

fun showImageCircleUrl(context: Context, view: ImageView, url: String) {
    Glide.with(context)
        .load(url)
        .fitCenter()
        .circleCrop()
        .placeholder(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_sync_24))
        .error(
            AppCompatResources.getDrawable(
                context,
                R.drawable.ic_baseline_image_not_supported_24
            )
        )
        .into(view)
}

fun showShortSnackBar(view: View, message: String) {
    Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
}