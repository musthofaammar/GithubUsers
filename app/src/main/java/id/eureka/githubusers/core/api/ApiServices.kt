package id.eureka.githubusers.core.api

import id.eureka.githubusers.users.data.model.UserDetailNetworkData
import id.eureka.githubusers.users.data.model.GetUsersModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiServices {

    @GET("search/users")
    suspend fun searchUsers(
        @Query("q") userName: String? = "\"\"",
        @Query("page") page: Int = 1,
        @Query("per_page") size: Int = 10
    ) : Response<GetUsersModel>

    @GET("users/{user}")
    suspend fun getUserByUsername(
        @Path("user") userName: String
    ) : Response<UserDetailNetworkData>

    @GET("users/{user}/repos")
    suspend fun searchRepositoriesByUser(
        @Path("user") userName: String,
        @Query("page") page: Int = 1,
        @Query("per_page") size: Int = 10
    )

}