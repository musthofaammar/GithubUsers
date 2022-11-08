package id.eureka.githubusers.core.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RemoteKeyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<RemoteKeys>)

    @Query("Select * from remote_keys where id = :id")
    suspend fun getRemoteKeysId(id : String) : RemoteKeys?

    @Query("Delete from remote_keys")
    suspend fun deleteRemoteKeys()
}