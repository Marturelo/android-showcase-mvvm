package pe.com.bcp.guidelineunittest.data.datasource.remote

import com.nhaarman.mockitokotlin2.mock
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.data.api.UserAPI
import pe.com.bcp.guidelineunittest.data.model.UserModel
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.utils.FakeValuesModel
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
    fun `given success Users when run then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesModel.users()
        coEvery { api.users() } returns Response.success(FakeValuesModel.users())

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right(fakeResult), result)
    }

    @Test
    fun `given null Response when run then verify result`() = runTest {
        //given
        coEvery { api.users() } returns Response.success(null)

        //when
        val result = dataSource.users()

        //then
        Assert.assertEquals(Either.Right<List<UserModel>>(listOf()), result)
    }

    @Test
    fun `given invalid params when run then verify result`() = runTest {
        //given
        coEvery { api.users() } returns Response.error(500, mock())

        //when
        val result = dataSource.users()

        //then
        Assert.assertTrue(result.isLeft)
        Assert.assertEquals(result, Either.Left(Failure.UnknownError))
    }
}