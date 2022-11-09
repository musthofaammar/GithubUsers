package id.eureka.githubusers.users.data.model.mapper

import id.eureka.githubusers.core.util.Mapper
import id.eureka.githubusers.users.data.model.UserData
import id.eureka.githubusers.users.data.model.UserNetworkData
import id.eureka.githubusers.users.data.model.UserEntity

object UserDataToUserEntity : Mapper<UserData, UserEntity>{
    override fun map(input: UserData) = UserEntity(
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