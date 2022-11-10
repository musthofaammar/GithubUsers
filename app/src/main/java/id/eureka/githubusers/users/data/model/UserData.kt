package id.eureka.githubusers.users.data.model

data class UserData(
    val id: Int,
    val bio: String?,
    val createdAt: String?,
    val login: String?,
    val updatedAt: String?,
    val company: String?,
    val publicRepos: Int?,
    val gravatarId: String?,
    val email: String?,
    val publicGists: Int?,
    val followers: Int?,
    val avatarUrl: String?,
    val following: Int?,
    val name: String?
)
