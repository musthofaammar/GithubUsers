package id.eureka.githubusers.core.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.eureka.githubusers.users.data.datasource.RepositoriesRepositoryImpl
import id.eureka.githubusers.users.data.datasource.UsersRepositoryImpl
import id.eureka.githubusers.users.domain.repository.RepositoriesRepository
import id.eureka.githubusers.users.domain.repository.UsersRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideUsersRepository(userRepositoryImpl: UsersRepositoryImpl): UsersRepository

    @Binds
    abstract fun provideRepositoriesRepository(repositoryImpl: RepositoriesRepositoryImpl): RepositoriesRepository
}