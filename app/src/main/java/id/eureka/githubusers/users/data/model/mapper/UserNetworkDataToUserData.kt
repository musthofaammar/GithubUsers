package id.eureka.githubusers.users.data.model.mapper

import id.eureka.githubusers.core.util.Mapper
import id.eureka.githubusers.users.data.model.UserDomain
import id.eureka.githubusers.users.data.model.UserNetworkData

object UserNetworkDataToUserData : Mapper<UserNetworkData, UserDomain> {
    override fun map(input: UserNetworkData) = UserDomain(
        id = input.id,
        bio = null,
        createdAt = null,
        login = input.login,
        updatedAt = null,
        company = null,
        publicRepos = null,
        gravatarId = input.gravatarId,
        email = null,
        publicGists = null,
        followers = null,
        avatarUrl = input.avatarUrl,
        following = null,
        name = null
    )
}