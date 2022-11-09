package id.eureka.githubusers.users.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import id.eureka.githubusers.users.domain.model.UserDomain
import id.eureka.githubusers.users.domain.model.mapper.UserDataToUserDomain
import id.eureka.githubusers.users.domain.repository.UsersRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SearchUserUseCase {
    suspend fun searchUser(userName: String): Flow<PagingData<UserDomain>>
}

class SearchUserUseCaseImpl @Inject constructor(private val usersRepository: UsersRepository) :
    SearchUserUseCase {
    override suspend fun searchUser(userName: String): Flow<PagingData<UserDomain>> =
        usersRepository.searchUsers(userName).map { pagingData ->
            pagingData.map { userData ->
                UserDataToUserDomain.map(userData)
            }
        }
}