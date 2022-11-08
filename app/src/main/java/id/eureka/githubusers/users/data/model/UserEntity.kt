package id.eureka.githubusers.users.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey
    val id : Int,
    val gistsUrl: String? = null,
    val reposUrl: String? = null,
    val followingUrl: String? = null,
    val twitterUsername: String? = null,
    val bio: String? = null,
    val createdAt: String? = null,
    val login: String? = null,
    val type: String? = null,
    val blog: String? = null,
    val subscriptionsUrl: String? = null,
    val updatedAt: String? = null,
    val siteAdmin: Boolean? = null,
    val company: String? = null,
    val publicRepos: Int? = null,
    val gravatarId: String? = null,
    val email: String? = null,
    val organizationsUrl: String? = null,
    val hireable: String? = null,
    val starredUrl: String? = null,
    val followersUrl: String? = null,
    val publicGists: Int? = null,
    val url: String? = null,
    val receivedEventsUrl: String? = null,
    val followers: Int? = null,
    val avatarUrl: String? = null,
    val eventsUrl: String? = null,
    val htmlUrl: String? = null,
    val following: Int? = null,
    val name: String? = null,
    val location: String? = null,
    val nodeId: String? = null
)