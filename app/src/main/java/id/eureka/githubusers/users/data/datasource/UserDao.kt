package id.eureka.githubusers.users.data.datasource

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import id.eureka.githubusers.users.data.model.UserEntity

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(list: List<UserEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(entity: UserEntity)

    @Query("Select * from users where id = :userId")
    fun getUser(userId : Int) : UserEntity

    @Query("Select * from users")
    fun getUsers(): PagingSource<Int, UserEntity>

    @Query("Select * from users where login like :query")
    fun getUsers(query : String) : PagingSource<Int, UserEntity>

    @Query("Select * from users where updatedAt is not null")
    fun getUsersWithFullData() : List<UserEntity>

    @Query("Delete from users where updatedAt is null")
    suspend fun deleteUsers()
}