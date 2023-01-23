package pe.com.bcp.guidelineunittest.domain.usecase

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.domain.core.UseCase
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository
import pe.com.bcp.guidelineunittest.exception.Failure
import javax.inject.Inject

open class GetUsersUseCase @Inject constructor(private val repository: UserRepository) :
    UseCase<Either<Failure, List<UserEntity>>, UseCase.None>() {
    override suspend fun run(params: None): Either<Failure, List<UserEntity>> {
        return repository.users()
    }
}