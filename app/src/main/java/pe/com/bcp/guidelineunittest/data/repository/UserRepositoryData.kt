package pe.com.bcp.guidelineunittest.data.repository

import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.commons.utils.map
import pe.com.bcp.guidelineunittest.data.datasource.UserDataSource
import pe.com.bcp.guidelineunittest.data.model.toEntity
import pe.com.bcp.guidelineunittest.domain.entity.UserEntity
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository
import pe.com.bcp.guidelineunittest.exception.Failure
import javax.inject.Inject

open class UserRepositoryData @Inject constructor(private val remoteDataSource: UserDataSource) :
    UserRepository {
    override suspend fun users(): Either<Failure, List<UserEntity>> {
        return remoteDataSource.users().map { result ->
            result.map { it.toEntity() }
        }
    }
}