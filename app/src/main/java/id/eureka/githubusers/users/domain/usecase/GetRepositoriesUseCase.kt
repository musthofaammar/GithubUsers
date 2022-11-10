package id.eureka.githubusers.users.domain.usecase

import androidx.paging.PagingData
import id.eureka.githubusers.users.presentation.model.Repository
import kotlinx.coroutines.flow.Flow

interface GetRepositoriesUseCase{
    suspend fun getRepositories() : Flow<PagingData<Repository>>
}

class GetRepositoriesUseCaseImpl : GetRepositoriesUseCase{
    override suspend fun getRepositories(): Flow<PagingData<Repository>> {
        TODO("Not yet implemented")
    }
}