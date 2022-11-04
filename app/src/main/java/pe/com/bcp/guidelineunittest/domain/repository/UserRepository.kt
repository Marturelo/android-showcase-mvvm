package pe.com.bcp.guidelineunittest.domain.repository

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity
import pe.com.bcp.guidelineunittest.exception.Failure

interface UserRepository {
    suspend fun users(): Either<Failure, List<UserEntity>>
}