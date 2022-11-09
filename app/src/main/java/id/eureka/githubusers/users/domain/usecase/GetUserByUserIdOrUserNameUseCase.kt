package id.eureka.githubusers.users.domain.usecase

import id.eureka.githubusers.core.model.Result
import id.eureka.githubusers.users.domain.model.UserDomain
import id.eureka.githubusers.users.domain.model.mapper.UserDataToUserDomain
import id.eureka.githubusers.users.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface GetUserByUserIdOrUserNameUseCase {
    suspend fun getUserByUserIdOrUserName(userName: String, userId: Int): Flow<Result<UserDomain>>
}

class GetUserByUserIdOrUserNameUseCaseImpl @Inject constructor(private val repository: UsersRepository) :
    GetUserByUserIdOrUserNameUseCase {
    override suspend fun getUserByUserIdOrUserName(
        userName: String,
        userId: Int
    ): Flow<Result<UserDomain>> = repository.getUserDetail(userName, userId).map { result ->
        when (result) {
            is Result.Success -> Result.Success(UserDataToUserDomain.map(result.data))
            is Result.Error -> result
        }
    }

}