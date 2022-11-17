package id.eureka.githubusers.core.di.module

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.eureka.githubusers.BuildConfig
import id.eureka.githubusers.core.api.ApiServices
import id.eureka.githubusers.core.api.AuthInterceptor
import id.eureka.githubusers.core.provider.ResourceProvider
import id.eureka.githubusers.core.util.ErrorMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
open class NetworkModule {

    protected open fun getBaseUrl(): String = BuildConfig.BASE_URL

    @Provides
    @Singleton
    fun provideOkHttp(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .addInterceptor(AuthInterceptor(BuildConfig.USERNAME_GITHUB, BuildConfig.TOKEN_GITHUB))
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .client(okHttpClient)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideErrorMapper(resourceProvider: ResourceProvider) = ErrorMapper(resourceProvider)

}