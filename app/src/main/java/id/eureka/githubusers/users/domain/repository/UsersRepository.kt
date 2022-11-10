package id.eureka.githubusers.users.domain.repository

import androidx.paging.PagingData
import id.eureka.githubusers.users.data.model.UserNetworkData
import kotlinx.coroutines.flow.Flow
import id.eureka.githubusers.core.model.Result
import id.eureka.githubusers.users.data.model.UserData
import id.eureka.githubusers.users.domain.model.UserDomain

interface UsersRepository {
    suspend fun searchUsers(userName : String = "") : Flow<PagingData<UserDomain>>
    suspend fun getUserDetail(userName: String, userId : Int) : Flow<Result<UserDomain>>
}