package id.eureka.githubusers.users.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.eureka.githubusers.users.data.model.RepositoryEntity

@Dao
interface RepositoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRepositories(list: List<RepositoryEntity>)

    @Query("Select * from repositories where userId is :userId")
    fun getRepositoriesByUserId(userId : Int): PagingSource<Int, RepositoryEntity>

    @Query("Delete from repositories")
    suspend fun deleteRepositories()
}