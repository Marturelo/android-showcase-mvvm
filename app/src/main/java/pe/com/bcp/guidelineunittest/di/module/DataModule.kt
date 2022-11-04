package pe.com.bcp.guidelineunittest.di.module

import dagger.Module
import dagger.Provides
import dagger.Reusable
import pe.com.bcp.guidelineunittest.data.datasource.UserDataSource
import pe.com.bcp.guidelineunittest.data.datasource.remote.UserDataSourceRemote
import pe.com.bcp.guidelineunittest.data.repository.UserRepositoryData
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository

@Module
class DataModule {

    @Provides
    @Reusable
    fun provideUserDataSourceRemote(dataSource: UserDataSourceRemote): UserDataSource {
        return dataSource
    }

    @Provides
    @Reusable
    fun provideUserRepository(repository: UserRepositoryData): UserRepository {
        return repository
    }
}