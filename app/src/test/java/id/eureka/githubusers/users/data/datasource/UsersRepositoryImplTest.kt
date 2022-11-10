package id.eureka.githubusers.users.data.datasource

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.map
import androidx.recyclerview.widget.ListUpdateCallback
import app.cash.turbine.test
import id.eureka.githubusers.MainDispatcherRule
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.core.provider.DispatcherProvider
import id.eureka.githubusers.core.provider.ResourceProvider
import id.eureka.githubusers.users.data.model.UserDomain
import id.eureka.githubusers.users.domain.repository.UsersRepository
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
import java.util.*
import kotlin.random.Random

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class UsersRepositoryImplTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var remoteKeysDao: RemoteKeyDao

    @Mock
    lateinit var services: ApiServices

    @Mock
    lateinit var resourceProvider: ResourceProvider

    @Mock
    lateinit var dispatcherProvider: DispatcherProvider

    private lateinit var storyRepository: UsersRepository

    @Before
    fun setUp() {
        storyRepository = UsersRepositoryImpl(
            userDao,
            remoteKeysDao,
            services,
            resourceProvider,
            dispatcherProvider
        )
    }

    @Test
    fun `when get all users should return correctly`() = runBlocking {
        val mockUserData = mockDummyUsers()
        val mockPagingData = PagingData.from(mockUserData)
        val mockFlow = flow {
            emit(mockPagingData)
        }
//        Mockito.`when`(userDao.getUsers()).thenReturn()

        val differ = AsyncPagingDataDiffer(
            diffCallback = UserAdapter.userDiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        storyRepository.searchUsers("").test {
//            differ.submitData(awaitItem().map { UserDoma.map(it) })
            awaitComplete()
        }

        val data = differ.snapshot()

        assertNotNull(data)
    }

    private fun mockDummyUsers(): List<UserDomain> {
        val list = mutableListOf<UserDomain>()
        for (i in 0..(1..10).random()) {
            list.add(
                UserDomain(
                    i,
                    UUID.randomUUID().toString(),
                    Random.toString(),
                    Random.toString(),
                    Random.toString(),
                    Random.toString(),
                    0,
                    "",
                    Random.toString(),
                    0,
                    0,
                    Random.toString(),
                    0,
                    Random.toString()
                )
            )
        }

        return list
    }

}


val noopListUpdateCallback = object : ListUpdateCallback {
    override fun onInserted(position: Int, count: Int) {}
    override fun onRemoved(position: Int, count: Int) {}
    override fun onMoved(fromPosition: Int, toPosition: Int) {}
    override fun onChanged(position: Int, count: Int, payload: Any?) {}
}