package pe.com.bcp.guidelineunittest.domain.usecase

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.data.repository.DummyUserRepository
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity
import pe.com.bcp.guidelineunittest.exception.Failure

class MockGetUsersUseCase : GetUsersUseCase(DummyUserRepository()) {

    var runResult: Either<Failure, List<UserEntity>> = Either.Right(emptyList())
    var runExactly = 0

    override suspend fun run(params: None): Either<Failure, List<UserEntity>> {
        runExactly += 1
        return runResult
    }
}