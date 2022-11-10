package id.eureka.githubusers.users.domain.usecase

import androidx.paging.PagingData
import androidx.paging.map
import id.eureka.githubusers.users.domain.repository.UsersRepository
import id.eureka.githubusers.users.presentation.model.User
import id.eureka.githubusers.users.presentation.model.mapper.UserDomainToUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface SearchUserUseCase {
    suspend fun searchUser(userName: String): Flow<PagingData<User>>
}

class SearchUserUseCaseImpl @Inject constructor(private val usersRepository: UsersRepository) :
    SearchUserUseCase {
    override suspend fun searchUser(userName: String): Flow<PagingData<User>> =
        usersRepository.searchUsers(userName).map { pagingData ->
            pagingData.map { userData ->
                UserDomainToUser.map(userData)
            }
        }
}