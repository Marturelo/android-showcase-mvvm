package pe.com.bcp.guidelineunittest.data.datasource

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.data.model.UserModel
import pe.com.bcp.guidelineunittest.exception.Failure

interface UserDataSource {
    suspend fun users(): Either<Failure, List<UserModel>>
}