package id.eureka.githubusers

import id.eureka.githubusers.users.data.model.RepositoryData
import id.eureka.githubusers.users.domain.model.RepositoryDomain
import id.eureka.githubusers.users.domain.model.UserDomain
import java.util.*
import kotlin.random.Random

object MockUtil {
    fun mockDummyUsers(): List<UserDomain> {
        val list = mutableListOf<UserDomain>()
        for (i in 0..(1..10).random()) {
            list.add(
                UserDomain(
                    i,
                    UUID.randomUUID().toString(),
                    toString(),
                    toString(),
                    toString(),
                    toString(),
                    0,
                    "",
                    toString(),
                    0,
                    0,
                    toString(),
                    0,
                    toString()
                )
            )
        }

        return list
    }

    fun mockDummyUser(): UserDomain {
        return UserDomain(
            Random.nextInt(1, 100),
            UUID.randomUUID().toString(),
            toString(),
            toString(),
            toString(),
            toString(),
            0,
            "",
            toString(),
            0,
            0,
            toString(),
            0,
            toString()
        )
    }

    fun mockDummyRepositories(): List<RepositoryData> {
        val list = mutableListOf<RepositoryData>()
        for (i in 0..(1..10).random()) {
            list.add(
                RepositoryData(
                    i,
                    i,
                    false,
                    0,
                    false,
                    "",
                    "",
                    false,
                    0,
                    "",
                    Random.toString(),
                    0,
                    Random.toString(),
                    "",
                    false,
                    false,
                    0,
                    Random.toString(),
                    Random.toString(),
                    0,
                    hasProjects = false,
                    archived = false,
                    hasWiki = false,
                    updatedAt = "",
                    disabled = false,
                    hasPages = false,
                    hasIssues = false,
                    webCommitSignoffRequired = false,
                    fork = false,
                    openIssues = 0,
                    watchersCount = 0,
                    nodeId = "",
                    homepage = "",
                    forksCount = 0
                )
            )
        }

        return list
    }
}