package id.eureka.githubusers.users.domain.usecase

import androidx.paging.AsyncPagingDataDiffer
import androidx.paging.PagingData
import androidx.paging.map
import app.cash.turbine.test
import id.eureka.githubusers.MainDispatcherRule
import id.eureka.githubusers.MockUtil
import id.eureka.githubusers.noopListUpdateCallback
import id.eureka.githubusers.users.domain.model.mapper.RepositoryDataToRepositoryDomain
import id.eureka.githubusers.users.domain.repository.RepositoriesRepository
import id.eureka.githubusers.users.presentation.detailuser.RepositoryAdapter
import id.eureka.githubusers.users.presentation.model.mapper.RepositoryDomainToRepository
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
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.random.Random

@RunWith(MockitoJUnitRunner::class)
class GetRepositoriesUseCaseImplTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var getRepositoriesUseCase: GetRepositoriesUseCase

    @Mock
    lateinit var repository: RepositoriesRepository

    @Before
    fun setUp() {
        getRepositoriesUseCase = GetRepositoriesUseCaseImpl(repository)
    }

    @Test
    fun `when get repositories should return correctly`() = runBlocking {
        val mockRepositories = MockUtil.mockDummyRepositories().map {
            RepositoryDataToRepositoryDomain.map(it)
        }
        val mockPagingData = PagingData.from(mockRepositories)
        val mockFlow = flow {
            emit(mockPagingData)
        }
        val mockUserName = Random.toString()
        val mockUserId = Random.nextInt(1, 10)
        `when`(repository.getRepositories(mockUserName, mockUserId)).thenReturn(mockFlow)

        val differ = AsyncPagingDataDiffer(
            diffCallback = RepositoryAdapter.repositoryDiffCallback,
            updateCallback = noopListUpdateCallback,
            workerDispatcher = Dispatchers.Main
        )

        repository.getRepositories(mockUserName, mockUserId).test {
            differ.submitData(awaitItem().map { RepositoryDomainToRepository.map(it) })
            awaitComplete()
        }

        val data = differ.snapshot()

        assertNotNull(data)
        assertEquals(mockRepositories.first().id, data.first()?.id)
        assertEquals(mockRepositories.last().id, data.last()?.id)
    }

}