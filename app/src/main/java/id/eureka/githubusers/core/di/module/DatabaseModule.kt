package id.eureka.githubusers.core.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import id.eureka.githubusers.core.database.GithubUsersDatabase
import id.eureka.githubusers.users.data.datasource.local.UserDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun providesLocalDatabase(@ApplicationContext context: Context): GithubUsersDatabase =
        Room.databaseBuilder(context, GithubUsersDatabase::class.java, "githubUsersDatabase")
            .fallbackToDestructiveMigration()
            .build()

    @Provides
    @Singleton
    fun providesStoryDao(database: GithubUsersDatabase): UserDao = database.userDao()
}