package pe.com.bcp.guidelineunittest.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import pe.com.bcp.guidelineunittest.BuildConfig
import pe.com.bcp.guidelineunittest.commons.Constants.API.API_CONNECTION_TIMEOUT
import pe.com.bcp.guidelineunittest.commons.Constants.API.ISO_DATE_FORMAT
import pe.com.bcp.guidelineunittest.data.api.UserAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkModule {
    fun providesOkHttpClientBuilder(
    ): OkHttpClient {
        val builder: OkHttpClient.Builder = OkHttpClient.Builder().apply {
            addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
        }
        builder.readTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.writeTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.connectTimeout(API_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
        builder.retryOnConnectionFailure(false)
        return builder.build()
    }

    fun providesGson(): Gson {
        val builder = GsonBuilder()
        builder.setDateFormat(ISO_DATE_FORMAT)
        builder.setLenient()
        return builder.create()
    }

    fun providesRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        val builder = Retrofit.Builder()
        builder.baseUrl(BuildConfig.HOST)
        builder.client(client)
        builder.addConverterFactory(GsonConverterFactory.create(gson))
        return builder.build()
    }

    fun provideApi(retrofit: Retrofit): UserAPI {
        return retrofit.create(UserAPI::class.java)
    }
}