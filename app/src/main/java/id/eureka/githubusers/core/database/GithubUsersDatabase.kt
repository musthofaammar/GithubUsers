package id.eureka.githubusers.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.eureka.githubusers.users.data.datasource.local.RepositoryDao
import id.eureka.githubusers.users.data.datasource.local.UserDao
import id.eureka.githubusers.users.data.model.RepositoryEntity
import id.eureka.githubusers.users.data.model.UserEntity

@Database(entities = [UserEntity::class, RepositoryEntity::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class GithubUsersDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
    abstract fun repositoryDao() : RepositoryDao
    abstract fun remoteKeyDao() : RemoteKeyDao
}