package id.eureka.githubusers.users.domain.repository

import androidx.paging.PagingData
import id.eureka.githubusers.users.domain.model.RepositoryDomain
import kotlinx.coroutines.flow.Flow

interface RepositoriesRepository {
    suspend fun getRepositories(userName: String, userId : Int): Flow<PagingData<RepositoryDomain>>
}