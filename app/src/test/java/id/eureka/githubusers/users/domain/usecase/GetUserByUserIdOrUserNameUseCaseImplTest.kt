package id.eureka.githubusers.users.domain.usecase

import app.cash.turbine.test
import id.eureka.githubusers.MainDispatcherRule
import id.eureka.githubusers.MockUtil
import id.eureka.githubusers.core.model.Result
import id.eureka.githubusers.users.domain.repository.UsersRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class GetUserByUserIdOrUserNameUseCaseImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var getUserByUserIdOrUserNameUseCase: GetUserByUserIdOrUserNameUseCase

    @Mock
    lateinit var repository: UsersRepository

    @Before
    fun setUp() {
        getUserByUserIdOrUserNameUseCase = GetUserByUserIdOrUserNameUseCaseImpl(repository)
    }

    @Test
    fun `when get user detail should return correctly`() = runBlocking {
        val mockUser = MockUtil.mockDummyUser()
        val mockResult = Result.Success(mockUser)
        val mockFlow = flow {
            emit(mockResult)
        }
        val mockUserName = toString()
        val mockUserId = Random.nextInt(1, 10)
        `when`(repository.getUserDetail(mockUserName, mockUserId)).thenReturn(mockFlow)

        repository.getUserDetail(mockUserName, mockUserId).test {
            val result = awaitItem()
            assert(result is Result.Success)
            val data = (result as Result.Success).data
            assertEquals(mockUser.id, data.id)
            awaitComplete()
        }
    }

}