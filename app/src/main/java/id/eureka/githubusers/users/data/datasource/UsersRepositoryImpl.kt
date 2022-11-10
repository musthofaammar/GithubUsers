package id.eureka.githubusers.users.data.datasource

import androidx.paging.*
import id.eureka.githubusers.R
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.core.model.Result
import id.eureka.githubusers.core.provider.DispatcherProvider
import id.eureka.githubusers.core.provider.ResourceProvider
import id.eureka.githubusers.users.data.model.mapper.UserDataToUserEntity
import id.eureka.githubusers.users.data.model.mapper.UserDetailNetworkDataToUserData
import id.eureka.githubusers.users.data.model.mapper.UserEntityToUserData
import id.eureka.githubusers.users.domain.model.UserDomain
import id.eureka.githubusers.users.domain.model.mapper.UserDataToUserDomain
import id.eureka.githubusers.users.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val services: ApiServices,
    private val resourceProvider: ResourceProvider,
    private val dispatcherProvider: DispatcherProvider
) : UsersRepository {
    override suspend fun searchUsers(userName: String): Flow<PagingData<UserDomain>> {
        return Pager(
            config = PagingConfig(
                pageSize = UsersRemoteMediator.NETWORK_CALL_SIZE,
                enablePlaceholders = true
            ),
            remoteMediator = UsersRemoteMediator(
                userDao,
                remoteKeyDao,
                services,
                userName
            ),
            pagingSourceFactory = {
                userDao.getUsers()
            }
        ).flow.flowOn(dispatcherProvider.getIO()).mapLatest { paging ->
            paging.map { userEntity ->
                UserDataToUserDomain.map(UserEntityToUserData.map(userEntity))
            }
        }
    }

    override suspend fun getUserDetail(userName: String, userId: Int): Flow<Result<UserDomain>> =
        flow {
            try {
                val response = services.getUserByUsername(userName)
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        userDao.insertUser(
                            UserDataToUserEntity.map(
                                UserDetailNetworkDataToUserData.map(
                                    response.body()!!
                                )
                            )
                        )
                    }
                }


                val user = userDao.getUser(userId)
                val userData = UserEntityToUserData.map(user)
                emit(Result.Success(userData))
            } catch (e: Exception) {
                emit(
                    Result.Error(
                        e.hashCode(),
                        e.localizedMessage ?: resourceProvider.getString(R.string.something_wrong)
                    )
                )
            }
        }.flowOn(dispatcherProvider.getIO()).map { result ->
            when (result) {
                is Result.Error -> result
                is Result.Success -> Result.Success(UserDataToUserDomain.map(result.data))
            }
        }
}