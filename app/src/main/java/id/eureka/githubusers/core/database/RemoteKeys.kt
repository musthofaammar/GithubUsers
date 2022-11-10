package id.eureka.githubusers.core.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey val id: String,
    val prevKey: Int?,
    val nextKey: Int?,
    val keyTag: Int
)

/*Note
* keyTag 1 for users
* keyTag 2 for repositories
* */

