package id.eureka.githubusers.core.provider

import android.content.Context
import javax.inject.Inject

interface ResourceProvider {
    fun getString(id: Int): String
}


class ResourceProviderImpl @Inject constructor(private val context: Context) : ResourceProvider {
    override fun getString(id: Int): String {
        return context.getString(id)
    }
}