package id.eureka.githubusers.core.di.module

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.eureka.githubusers.users.domain.usecase.*

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    abstract fun provideSearchUserUseCase(searchUserUseCaseImpl: SearchUserUseCaseImpl): SearchUserUseCase

    @Binds
    abstract fun provideGetUserByUserIdOrUserNameUseCase(getUserByUserIdOrUserNameUseCaseImpl: GetUserByUserIdOrUserNameUseCaseImpl): GetUserByUserIdOrUserNameUseCase

    @Binds
    abstract fun provideGetRepositoriesUseCase(getRepositoriesUseCaseImpl: GetRepositoriesUseCaseImpl): GetRepositoriesUseCase
}