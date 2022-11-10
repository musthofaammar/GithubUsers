package id.eureka.githubusers.users.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val bio: String,
    val createdAt: String,
    val login: String,
    val updatedAt: String,
    val company: String,
    val publicRepos: Int,
    val gravatarId: String,
    val email: String,
    val publicGists: Int,
    val followers: Int,
    val avatarUrl: String,
    val following: Int,
    val name: String
) : Parcelable
