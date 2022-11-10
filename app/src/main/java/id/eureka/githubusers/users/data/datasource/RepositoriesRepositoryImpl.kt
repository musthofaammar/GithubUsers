package id.eureka.githubusers.users.data.datasource

import androidx.paging.*
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.core.provider.DispatcherProvider
import id.eureka.githubusers.users.data.model.mapper.RepositoryEntityToRepositoryData
import id.eureka.githubusers.users.domain.model.RepositoryDomain
import id.eureka.githubusers.users.domain.model.mapper.RepositoryDataToRepositoryDomain
import id.eureka.githubusers.users.domain.repository.RepositoriesRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class RepositoriesRepositoryImpl @Inject constructor(
    private val repositoryDao: RepositoryDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val services: ApiServices,
    private val dispatcherProvider: DispatcherProvider,
) : RepositoriesRepository {
    override suspend fun getRepositories(
        userName: String,
        userId: Int,
    ): Flow<PagingData<RepositoryDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = RepositoriesRemoteMediator.NETWORK_CALL_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = RepositoriesRemoteMediator(
                repositoryDao,
                remoteKeyDao,
                services,
                userName,
                userId
            ),
            pagingSourceFactory = {
                repositoryDao.getRepositoriesByUserId(userId)
            }
        ).flow.flowOn(dispatcherProvider.getIO()).mapLatest { paging ->
            paging.map { userEntity ->
                RepositoryDataToRepositoryDomain.map(RepositoryEntityToRepositoryData.map(userEntity))
            }
        }
    }
}