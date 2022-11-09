package id.eureka.githubusers.users.domain.model

data class UserDomain(
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
)
