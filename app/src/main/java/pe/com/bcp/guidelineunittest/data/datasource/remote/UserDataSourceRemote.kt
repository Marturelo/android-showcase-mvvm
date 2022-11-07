package pe.com.bcp.guidelineunittest.data.datasource.remote

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.data.api.UserAPI
import pe.com.bcp.guidelineunittest.data.datasource.UserDataSource
import pe.com.bcp.guidelineunittest.data.model.UserModel
import pe.com.bcp.guidelineunittest.exception.Failure
import javax.inject.Inject

class UserDataSourceRemote @Inject constructor(private val api: UserAPI) : UserDataSource {
    override suspend fun users(): Either<Failure, List<UserModel>> {
        val response = api.users()
        return when (response.isSuccessful) {
            true -> Either.Right(response.body() ?: listOf())
            false -> Either.Left(Failure.UnknownError)
        }

    }
}