package id.eureka.githubusers.core.util

import id.eureka.githubusers.R
import id.eureka.githubusers.core.provider.ResourceProvider
import org.json.JSONObject
import javax.inject.Inject

class ErrorMapper @Inject constructor(
    private val resourceProvider: ResourceProvider
) {
    fun getErrorMessageFromBody(errorBody: String?) : String{
        val message: String = if (errorBody != null){
            val jsonError = JSONObject(errorBody)
            jsonError.getString("message") ?: resourceProvider.getString(R.string.something_wrong)
        } else {
            resourceProvider.getString(R.string.something_wrong)
        }

        return message
    }

}