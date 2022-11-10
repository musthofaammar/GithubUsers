package id.eureka.githubusers.users.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import id.eureka.githubusers.MainDispatcherRule
import id.eureka.githubusers.MockUtil
import id.eureka.githubusers.noopListUpdateCallback
import id.eureka.githubusers.users.domain.repository.UsersRepository
import id.eureka.githubusers.users.presentation.model.mapper.UserDomainToUser
import id.eureka.githubusers.users.presentation.users.UserAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class SearchUserUseCaseImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var searchUserUseCase: SearchUserUseCase

    @Mock
    lateinit var repository: UsersRepository

    @Before
    fun setUp() {
        searchUserUseCase = SearchUserUseCaseImpl(repository)
    }

    @Test
    fun `when search users should return correctly`() = runBlocking {
        val mockUsers = MockUtil.mockDummyUsers()
        val mockPagingData = PagingData.from(mockUsers)
        val mockFlow = flow {
            emit(mockPagingData)
        }
        val mockUserName = Random.toString()
        Mockito.`when`(repository.searchUsers(mockUserName)).thenReturn(mockFlow)

        val differ = AsyncPagingDataDiffer(
            diffCallback = UserAdapter.userDiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        repository.searchUsers(mockUserName).test {
            differ.submitData(awaitItem().map { UserDomainToUser.map(it) })
            awaitComplete()
        }

        val data = differ.snapshot()

        assertNotNull(data)
        assertEquals(mockUsers.first().id, data.first()?.id)
        assertEquals(mockUsers.last().id, data.last()?.id)
    }
}