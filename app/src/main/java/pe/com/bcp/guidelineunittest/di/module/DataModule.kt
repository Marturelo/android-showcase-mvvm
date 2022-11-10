package pe.com.bcp.guidelineunittest.di.module

import pe.com.bcp.guidelineunittest.data.datasource.UserDataSource
import pe.com.bcp.guidelineunittest.data.datasource.remote.UserDataSourceRemote
import pe.com.bcp.guidelineunittest.data.repository.UserRepositoryData
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository

object DataModule {

    fun provideUserDataSourceRemote(dataSource: UserDataSourceRemote): UserDataSource {
        return dataSource
    }

    fun provideUserRepository(repository: UserRepositoryData): UserRepository {
        return repository
    }
}