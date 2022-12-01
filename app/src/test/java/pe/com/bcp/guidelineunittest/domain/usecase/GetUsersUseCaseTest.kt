package pe.com.bcp.guidelineunittest.domain.usecase

import io.mockk.*
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import pe.com.bcp.guidelineunittest.commons.utils.Either
import pe.com.bcp.guidelineunittest.commons.utils.getOrElse
import pe.com.bcp.guidelineunittest.domain.core.UseCase
import pe.com.bcp.guidelineunittest.domain.repository.UserRepository
import pe.com.bcp.guidelineunittest.exception.Failure
import pe.com.bcp.guidelineunittest.utils.FakeValuesEntity

class GetUsersUseCaseTest {

    @MockK(relaxed = true)
    lateinit var userRepository: UserRepository

    lateinit var useCase: GetUsersUseCase

    @Before
    @Throws(Exception::class)
    fun setUp() {
        MockKAnnotations.init(this)
        useCase = spyk(GetUsersUseCase(userRepository))
    }

    @Test
    fun `given valid params when run then verify result`() = runTest {
        //given
        val fakeResult = FakeValuesEntity.users()
        coEvery { userRepository.users() } returns Either.Right(fakeResult)

        //when
        val result = useCase.run(UseCase.None)

        //then
        Assert.assertEquals(fakeResult.size, result.getOrElse(listOf()).size)
    }

    @Test
    fun `given invalid params when run then verify result`() = runTest {
        //given
        coEvery { userRepository.users() } returns Either.Left(Failure.NetworkConnection)

        //when
        val result = useCase.run(UseCase.None)

        //then
        Assert.assertTrue(result.isLeft)
    }

}