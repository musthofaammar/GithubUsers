package id.eureka.githubusers.users

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import id.eureka.githubusers.core.database.GithubUsersDatabase
import id.eureka.githubusers.core.database.RemoteKeyDao
import id.eureka.githubusers.core.di.module.DatabaseModule
import id.eureka.githubusers.users.data.datasource.RepositoryDao
import id.eureka.githubusers.users.data.datasource.UserDao
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class]
)
class FakeDatabaseModule {
    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context): GithubUsersDatabase =
        Room.inMemoryDatabaseBuilder(
            context,
            GithubUsersDatabase::class.java
        ).build()

    @Provides
    @Singleton
    fun providesUserDao(database: GithubUsersDatabase): UserDao = database.userDao()

    @Provides
    @Singleton
    fun providesRepositoryDao(database: GithubUsersDatabase): RepositoryDao =
        database.repositoryDao()

    @Provides
    @Singleton
    fun providesRemoteKeysDao(database: GithubUsersDatabase): RemoteKeyDao = database.remoteKeyDao()
}