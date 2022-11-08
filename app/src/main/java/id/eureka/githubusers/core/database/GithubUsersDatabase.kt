package id.eureka.githubusers.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import id.eureka.githubusers.users.data.datasource.local.UserDao
import id.eureka.githubusers.users.data.model.UserEntity

@Database(entities = [UserEntity::class], version = 1, exportSchema = false)
abstract class GithubUsersDatabase : RoomDatabase() {
    abstract fun userDao() : UserDao
}