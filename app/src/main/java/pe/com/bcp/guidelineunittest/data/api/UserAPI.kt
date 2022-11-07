package pe.com.bcp.guidelineunittest.data.api

import pe.com.bcp.guidelineunittest.data.model.UserModel
import retrofit2.Response
import retrofit2.http.GET

interface UserAPI {
    @GET("/users")
    suspend fun users(): Response<List<UserModel>>
}