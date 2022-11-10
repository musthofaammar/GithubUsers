package id.eureka.githubusers.users.data.datasource

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.core.database.RemoteKeys
import id.eureka.githubusers.users.data.model.RepositoryEntity
import id.eureka.githubusers.users.data.model.mapper.RepositoryDataToRepositoryEntity
import id.eureka.githubusers.users.data.model.mapper.RepositoryNetworkDataToRepositoryData

@OptIn(ExperimentalPagingApi::class)
class RepositoriesRemoteMediator(
    private val repositoryDao: RepositoryDao,
    private val remoteKeysDao: RemoteKeyDao,
    private val services: ApiServices,
    private val userName: String,
    private val userId : Int
) : RemoteMediator<Int, RepositoryEntity>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType, state: PagingState<Int, RepositoryEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: INITIAL_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                val prevKey = remoteKeys?.prevKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                val nextKey = remoteKeys?.nextKey ?: return MediatorResult.Success(
                    endOfPaginationReached = remoteKeys != null
                )
                nextKey
            }
        }

        try {
            val responseData = services.searchRepositoriesByUser(
                userName.ifEmpty { "\"\"" },
                page,
                state.config.pageSize
            )

            val endOfPaginationReached = responseData.body()?.isEmpty() ?: true

            val repositoryEntities = if (!endOfPaginationReached) {
                responseData.body()?.map {
                    RepositoryDataToRepositoryEntity.map(RepositoryNetworkDataToRepositoryData.map(it))
                } ?: emptyList()
            } else {
                emptyList()
            }

            if (loadType == LoadType.REFRESH && responseData.isSuccessful) {
                remoteKeysDao.deleteRepositoryRemoteKeys()
                repositoryDao.deleteUsersRepositories(userId)
            }

            val prevKey = if (page == 1) null else page - 1
            val nextKey = if (endOfPaginationReached) null else page + 1
            val keys = responseData.body()?.map {
                RemoteKeys(id = "r${it.id}", prevKey = prevKey, nextKey = nextKey, 1)
            }

            if (!keys.isNullOrEmpty()) {
                remoteKeysDao.insertAll(keys)
            }

            repositoryDao.insertRepositories(repositoryEntities)

            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (e: Exception) {
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, RepositoryEntity>): RemoteKeys? {
        return state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { data ->
            remoteKeysDao.getRepositoryRemoteKeysId("r${data.id}")
        }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, RepositoryEntity>): RemoteKeys? {
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { data ->
            remoteKeysDao.getRepositoryRemoteKeysId("r${data.id}")
        }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(state: PagingState<Int, RepositoryEntity>): RemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.id?.let { id ->
                remoteKeysDao.getRepositoryRemoteKeysId("r${id}")
            }
        }
    }

    companion object {
        const val INITIAL_PAGE_INDEX = 1
        const val NETWORK_CALL_SIZE = 25
    }
}