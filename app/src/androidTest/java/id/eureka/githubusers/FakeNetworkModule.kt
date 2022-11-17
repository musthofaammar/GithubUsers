package id.eureka.githubusers

import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import id.eureka.githubusers.core.di.module.NetworkModule

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [NetworkModule::class]
)
class FakeNetworkModule : NetworkModule() {
    override fun getBaseUrl(): String {
        return "http://localhost:8080/"
    }
}