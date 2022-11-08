package id.eureka.githubusers.core.api

import id.eureka.githubusers.BuildConfig
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(private val username: String, private val password: String) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val credential = Credentials.basic(username, password)

        var request = chain.request()

        request = request.newBuilder()
            .addHeader("Authorization", credential)
            .build()

        return chain.proceed(request)
    }
}