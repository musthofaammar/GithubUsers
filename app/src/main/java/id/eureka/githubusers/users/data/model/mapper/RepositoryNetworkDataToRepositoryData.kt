package id.eureka.githubusers.users.data.model.mapper

import id.eureka.githubusers.core.util.Mapper
import id.eureka.githubusers.users.data.model.RepositoryData
import id.eureka.githubusers.users.data.model.RepositoryNetworkData

object RepositoryNetworkDataToRepositoryData : Mapper<RepositoryNetworkData, RepositoryData> {

    override fun map(input: RepositoryNetworkData): RepositoryData =
        RepositoryData(
            id = input.id ?: 0,
            userId = input.owner?.id ?: 0,
            allowForking = input.allowForking ?: false,
            stargazersCount = input.stargazersCount ?: 0,
            isTemplate = input.isTemplate ?: false,
            pushedAt = input.pushedAt ?: "",
            language = input.language ?: "",
            hasDiscussions = input.hasDiscussions ?: false,
            forks = input.forks ?: 0,
            visibility = input.visibility ?: "",
            fullName = input.fullName ?: "",
            size = input.size ?: 0,
            name = input.name ?: "",
            defaultBranch = input.defaultBranch ?: "",
            jsonMemberPrivate = input.jsonMemberPrivate ?: false,
            hasDownloads = input.hasDownloads ?: false,
            openIssuesCount = input.openIssuesCount ?: 0,
            description = input.description ?: "",
            createdAt = input.createdAt ?: "",
            watchers = input.watchers ?: 0,
            hasProjects = input.hasProjects ?: false,
            archived = input.archived ?: false,
            hasWiki = input.hasWiki ?: false,
            updatedAt = input.updatedAt ?: "",
            disabled = input.disabled ?: false,
            hasIssues = input.hasIssues ?: false,
            hasPages = input.hasPages ?: false,
            webCommitSignoffRequired = input.webCommitSignoffRequired ?: false,
            fork = input.fork ?: false,
            openIssues = input.openIssues ?: 0,
            watchersCount = input.watchersCount ?: 0,
            nodeId = input.nodeId ?: "",
            homepage = input.homepage ?: "",
            forksCount = input.forksCount ?: 0
        )
}