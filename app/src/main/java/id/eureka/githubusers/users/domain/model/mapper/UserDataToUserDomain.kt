package id.eureka.githubusers.users.domain.model.mapper

import id.eureka.githubusers.core.util.Mapper
import id.eureka.githubusers.users.data.model.UserDomain
import id.eureka.githubusers.users.domain.model.UserDomain

object UserDataToUserDomain : Mapper<id.eureka.githubusers.users.data.model.UserDomain, UserDomain> {
    override fun map(input: id.eureka.githubusers.users.data.model.UserDomain): UserDomain = UserDomain(
        id = input.id,
        bio = input.bio ?: "",
        createdAt = input.createdAt ?: "",
        login = input.login ?: "",
        updatedAt = input.updatedAt ?: "",
        company = input.company ?: "",
        publicRepos = input.publicRepos ?: 0,
        gravatarId = input.gravatarId ?: "",
        email = input.email ?: "",
        publicGists = input.publicGists ?: 0,
        followers = input.followers ?: 0,
        avatarUrl = input.avatarUrl ?: "",
        following = input.following ?: 0,
        name = input.name ?: ""
    )
}