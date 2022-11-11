package pe.com.bcp.guidelineunittest.ext

import android.app.Application
import com.google.gson.Gson
import okhttp3.OkHttpClient
import pe.com.bcp.guidelineunittest.data.api.UserAPI
import pe.com.bcp.guidelineunittest.data.datasource.UserDataSource
import pe.com.bcp.guidelineunittest.data.datasource.remote.UserDataSourceRemote
import pe.com.bcp.guidelineunittest.data.repository.UserRepositoryData
import pe.com.bcp.guidelineunittest.di.KClassInfo
import pe.com.bcp.guidelineunittest.di.Locator
import pe.com.bcp.guidelineunittest.di.module.DataModule
import pe.com.bcp.guidelineunittest.di.module.NetworkModule
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository
import pe.com.bcp.guidelineunittest.domain.usecase.GetUsersUseCase
import retrofit2.Retrofit

fun Locator.create(app: Application) {
    //Network
    put(OkHttpClient::class, NetworkModule.providesOkHttpClientBuilder())
    put(Gson::class, NetworkModule.providesGson())
    put(Retrofit::class, NetworkModule.providesRetrofit(get(), get()))
    put(UserAPI::class, NetworkModule.provideApi(get()), KClassInfo.Type.Singleton)

    //Data
    put(
        UserDataSource::class,
        DataModule.provideUserDataSourceRemote(UserDataSourceRemote(get()))
    )
    put(
        UserRepository::class,
        DataModule.provideUserRepository(UserRepositoryData(get()))
    )

    put(GetUsersUseCase::class, GetUsersUseCase(get()))
}