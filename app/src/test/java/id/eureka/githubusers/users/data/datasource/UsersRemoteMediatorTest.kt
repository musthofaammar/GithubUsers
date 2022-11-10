package id.eureka.githubusers.users.data.datasource

import androidx.paging.*
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.users.data.model.*
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.random.Random

@OptIn(ExperimentalPagingApi::class)
@RunWith(MockitoJUnitRunner::class)
class UsersRemoteMediatorTest {
    @Mock
    lateinit var userDao: UserDao

    @Mock
    lateinit var remoteKeyDao: RemoteKeyDao

    @Mock
    lateinit var services: ApiServices

    private lateinit var usersRemoteMediator: UsersRemoteMediator

    private var mockUsername = Random.toString()

    @Before
    fun setUp() {
        usersRemoteMediator = UsersRemoteMediator(
            userDao, remoteKeyDao, services, mockUsername
        )
    }

    @Test
    fun `should refresh load success and end Of pagination when more data present correctly`() =
        runBlocking {
            val mockResponse = Response.success(
                GetUsersModel(
                    items = listOf(UserNetworkData()), incompleteResults = true, totalCount = 1
                )
            )

            `when`(services.searchUsers(mockUsername)).thenReturn(mockResponse)
            `when`(userDao.deleteUsers()).then { }
            `when`(remoteKeyDao.insertAll(Mockito.anyList())).then { }

            val pagingState = PagingState<Int, UserEntity>(
                listOf(), null, PagingConfig(10), 10
            )

            val result = usersRemoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `should refresh load success and end Of pagination when no more data present correctly`() =
        runBlocking {
            val mockResponse = Response.success(
                GetUsersModel(
                    items = listOf(), incompleteResults = true, totalCount = 1
                )
            )

            `when`(services.searchUsers(mockUsername)).thenReturn(mockResponse)
            val pagingState = PagingState<Int, UserEntity>(
                listOf(), null, PagingConfig(10), 10
            )

            val result = usersRemoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
}