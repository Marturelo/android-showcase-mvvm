package pe.com.bcp.guidelineunittest.data.datasource.remote

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.data.api.UserAPI
import pe.com.bcp.guidelineunittest.data.model.UserModel
import pe.com.bcp.guidelineunittest.exception.Failure
import retrofit2.Response

class UserDataSourceRemoteTest {
    @MockK(relaxed = true)
    private lateinit var api: UserAPI

    @InjectMockKs
    private lateinit var dataSource: UserDataSourceRemote

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun `given success Users when users then verify result`() = runTest {
        //given
        val fakeResult = mockk<List<UserModel>>()
        coEvery { api.users() } returns Response.success(fakeResult)

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right(fakeResult), result)
    }

    @Test
    fun `given null Response when users then verify result`() = runTest {
        //given
        coEvery { api.users() } returns Response.success(null)

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right<List<UserModel>>(listOf()), result)
    }

    @Test
    fun `given fail result when users then verify result`() = runTest {
        //given
        coEvery { api.users() } returns Response.error(500, byteArrayOf().toResponseBody())

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(result, Either.Left(Failure.UnknownError))
    }
}