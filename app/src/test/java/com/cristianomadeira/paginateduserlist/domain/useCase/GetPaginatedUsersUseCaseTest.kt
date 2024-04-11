package com.cristianomadeira.paginateduserlist.domain.useCase

import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.AlwaysOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.domain.repository.UserRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Job
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPaginatedUsersUseCaseTest {

    private val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
        optionalStrategy(AlwaysOptionalStrategy)
    }

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher + Job())

    @MockK
    lateinit var userRepository: UserRepository

    private lateinit var getPaginatedUsersUseCase: GetPaginatedUsersUseCase

    @Before
    fun prepare() {
        MockKAnnotations.init(this)
        getPaginatedUsersUseCase = GetPaginatedUsersUseCase(
            userRepository,
            testDispatcher
        )
    }

    @Test
    fun `when requests next users, successfully return paginated users`() = testScope.runTest {
        val response = listOf<User>(
            fixture(),
            fixture()
        )

        // Test getting first page
        coEvery { userRepository.getPaginatedUsers(1, 1) } returns Result.success(listOf(response[0]))

        assertThat(getPaginatedUsersUseCase.page).isEqualTo(1)

        val resultFirstPage = getPaginatedUsersUseCase.getNextUsers(1)

        coVerify { userRepository.getPaginatedUsers(1, 1) }

        resultFirstPage.fold(
            onSuccess = { users ->
                assertThat(users).isEqualTo(listOf(response[0]))
            },
            onFailure = {}
        )

        // Test getting second page
        coEvery { userRepository.getPaginatedUsers(2, 1) } returns Result.success(listOf(response[1]))

        assertThat(getPaginatedUsersUseCase.page).isEqualTo(2)

        val resultSecondPage = getPaginatedUsersUseCase.getNextUsers(1)

        coVerify { userRepository.getPaginatedUsers(2, 1) }

        resultSecondPage.fold(
            onSuccess = { users ->
                assertThat(users).isEqualTo(listOf(response[1]))
            },
            onFailure = {}
        )
    }

    @Test
    fun `when requests next users, request fails`() = testScope.runTest {
        val errorMessage = "No users found"

        coEvery { userRepository.getPaginatedUsers(any(), any()) } returns Result.failure(Exception(errorMessage))

        val result = getPaginatedUsersUseCase.getNextUsers(1)

        coVerify { userRepository.getPaginatedUsers(1, 1) }

        result.fold(
            onSuccess = {},
            onFailure = { error ->
                assertThat(error.message).isEqualTo(errorMessage)
            }
        )
    }
}