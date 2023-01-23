package pe.com.bcp.guidelineunittest.data.repository

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository
import pe.com.bcp.guidelineunittest.exception.Failure

class FakeUserRepository : UserRepository {
    override suspend fun users(): Either<Failure, List<UserEntity>> {
    }
}