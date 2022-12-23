@file:OptIn(ExperimentalCoroutinesApi::class)

package pe.com.bcp.guidelineunittest.data.datasource.remote

import com.google.gson.Gson
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.data.api.UserAPI
import pe.com.bcp.guidelineunittest.data.datasource.UserDataSource
import pe.com.bcp.guidelineunittest.data.model.UserModel
import pe.com.bcp.guidelineunittest.di.module.NetworkModule
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.utils.FakeValuesModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UserDataSourceRemoteTest {
    private lateinit var api: UserAPI
    lateinit var gson: Gson

    @get:Rule
    val mockWebServer = MockWebServer()

    private lateinit var dataSource: UserDataSource

    @Before
    fun setUp() {
        gson = NetworkModule.providesGson()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(NetworkModule.providesOkHttpClientBuilder())
            .build()
            .create(UserAPI::class.java)

        dataSource = UserDataSourceRemote(api)
    }

    @Test
    fun `given success Users when users then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesModel.users()
        mockWebServer.enqueue(MockResponse().apply {
            setBody(gson.toJson(fakeResult))
            setResponseCode(200)
        })

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right(fakeResult), result)
        Assert.assertFalse(result.isLeft)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `given empty Response when users then verify result`() = runTest {
        //given
        mockWebServer.enqueue(MockResponse().apply {
            setBody("[]")
            setResponseCode(200)
        })

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right<List<UserModel>>(listOf()), result)
        Assert.assertFalse(result.isLeft)
        Assert.assertTrue(result.isRight)
    }

    @Test
    fun `given fail result when users then verify result`() = runTest {
        //given
        mockWebServer.enqueue(MockResponse().apply {
            setBody("")
            setResponseCode(500)
        })

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(result, Either.Left(Failure.UnknownError))
        Assert.assertTrue(result.isLeft)
        Assert.assertFalse(result.isRight)
    }
}