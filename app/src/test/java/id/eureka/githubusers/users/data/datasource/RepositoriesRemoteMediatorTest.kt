package id.eureka.githubusers.users.data.datasource

import androidx.paging.*
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.users.data.model.RepositoryEntity
import id.eureka.githubusers.users.data.model.RepositoryNetworkData
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response
import kotlin.random.Random

@OptIn(ExperimentalPagingApi::class)
@RunWith(MockitoJUnitRunner::class)
class RepositoriesRemoteMediatorTest {

    @Mock
    lateinit var repositoryDao: RepositoryDao

    @Mock
    lateinit var remoteKeyDao: RemoteKeyDao

    @Mock
    lateinit var services: ApiServices

    private lateinit var repositoriesRemoteMediator: RepositoriesRemoteMediator

    private var mockUsername = Random.toString()
    private var mockUserId = Random.nextInt(1, 10)

    @Before
    fun setUp() {
        repositoriesRemoteMediator =
            RepositoriesRemoteMediator(
                repositoryDao,
                remoteKeyDao,
                services,
                mockUsername,
                mockUserId
            )
    }

    @Test
    fun `should refresh load success and end Of pagination when more data present correctly`() =
        runBlocking {
            val mockRepositoryId = Random.nextInt(1, 10)
            val mockResponse =
                Response.success(listOf(RepositoryNetworkData(id = mockRepositoryId)))

            `when`(services.searchRepositoriesByUser(mockUsername)).thenReturn(mockResponse)
            `when`(remoteKeyDao.deleteRepositoryRemoteKeys()).then {  }
            `when`(repositoryDao.deleteUsersRepositories(mockUserId)).then {  }
            `when`(remoteKeyDao.insertAll(anyList())).then {  }
            `when`(repositoryDao.insertRepositories(anyList())).then {  }

            val pagingState = PagingState<Int, RepositoryEntity>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            val result = repositoriesRemoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }

    @Test
    fun `should refresh load success and end Of pagination when no more data present correctly`() =
        runBlocking {
            val mockResponse =
                Response.success(listOf<RepositoryNetworkData>())

            `when`(services.searchRepositoriesByUser(mockUsername)).thenReturn(mockResponse)
            `when`(remoteKeyDao.deleteRepositoryRemoteKeys()).then {  }
            `when`(repositoryDao.deleteUsersRepositories(mockUserId)).then {  }
            `when`(repositoryDao.insertRepositories(anyList())).then {  }

            val pagingState = PagingState<Int, RepositoryEntity>(
                listOf(),
                null,
                PagingConfig(10),
                10
            )

            val result = repositoriesRemoteMediator.load(LoadType.REFRESH, pagingState)
            assertTrue(result is RemoteMediator.MediatorResult.Success)
            assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
        }
}