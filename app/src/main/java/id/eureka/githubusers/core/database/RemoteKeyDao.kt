package id.eureka.githubusers.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<RemoteKeys>)

    @Query("Select * from remote_keys where id = :id and keyTag = 1")
    suspend fun getUserRemoteKeysId(id : String) : RemoteKeys?

    @Query("Select * from remote_keys where id = :id and keyTag = 2")
    suspend fun getRepositoryRemoteKeysId(id : String) : RemoteKeys?

    @Query("Delete from remote_keys where keyTag = 1")
    suspend fun deleteUserRemoteKeys()

    @Query("Delete from remote_keys where keyTag = 2")
    suspend fun deleteRepositoryRemoteKeys()
}