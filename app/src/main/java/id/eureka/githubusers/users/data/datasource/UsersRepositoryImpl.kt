package id.eureka.githubusers.users.data.datasource

import androidx.paging.*
import id.eureka.githubusers.R
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.core.model.Result
import id.eureka.githubusers.core.provider.DispatcherProvider
import id.eureka.githubusers.core.provider.ResourceProvider
import id.eureka.githubusers.core.util.ErrorMapper
import id.eureka.githubusers.users.data.model.UserData
import id.eureka.githubusers.users.data.model.mapper.UserDataToUserEntity
import id.eureka.githubusers.users.data.model.mapper.UserDetailNetworkDataToUserData
import id.eureka.githubusers.users.data.model.mapper.UserEntityToUserData
import id.eureka.githubusers.users.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
class UsersRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val remoteKeyDao: RemoteKeyDao,
    private val services: ApiServices,
    private val errorMapper: ErrorMapper,
    private val resourceProvider: ResourceProvider,
    private val dispatcherProvider: DispatcherProvider
) : UsersRepository {
    override suspend fun searchUsers(userName: String): Flow<PagingData<UserData>> {
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
//                if (userName.isEmpty()) userDao.getUsers() else userDao.getUsers(
//                    userName
//                )
                userDao.getUsers()
            }
        ).flow.mapLatest { paging ->
            paging.map { userEntity ->
                UserEntityToUserData.map(userEntity)
            }
        }
    }

    override suspend fun getUserDetail(userName: String, userId: Int): Flow<Result<UserData>> =
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


                val userData = UserEntityToUserData.map(userDao.getUser(userId))
                emit(Result.Success(userData))
            } catch (e: Exception) {
                emit(
                    Result.Error(
                        e.hashCode(),
                        e.localizedMessage ?: resourceProvider.getString(R.string.something_wrong)
                    )
                )
            }
        }.flowOn(dispatcherProvider.getIO())
}