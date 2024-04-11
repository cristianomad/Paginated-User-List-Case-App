package com.cristianomadeira.paginateduserlist.ui.feature.users

import com.appmattus.kotlinfixture.decorator.nullability.NeverNullStrategy
import com.appmattus.kotlinfixture.decorator.nullability.nullabilityStrategy
import com.appmattus.kotlinfixture.decorator.optional.AlwaysOptionalStrategy
import com.appmattus.kotlinfixture.decorator.optional.optionalStrategy
import com.appmattus.kotlinfixture.kotlinFixture
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.domain.useCase.GetPaginatedUsersUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsersViewModelTest {
    private val fixture = kotlinFixture {
        nullabilityStrategy(NeverNullStrategy)
        optionalStrategy(AlwaysOptionalStrategy)
    }

    @MockK
    lateinit var getPaginatedUsersUseCase: GetPaginatedUsersUseCase

    private lateinit var viewModel: UsersViewModel

    @Before
    fun prepare() {
        MockKAnnotations.init(this)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    private fun initViewModel() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = UsersViewModel(getPaginatedUsersUseCase)
    }

    @Test
    fun `When request next users, return users successfully`() = runTest {
        val expected = listOf<User> (
            fixture(),
            fixture()
        )

        coEvery { getPaginatedUsersUseCase.getNextUsers(any()) } returns Result.success(expected)

        initViewModel()

        coVerify { getPaginatedUsersUseCase.getNextUsers(15) }

        assertThat(viewModel.screenState.value.users).isEqualTo(expected)
    }

    @Test
    fun `When request next users, return error`() = runTest {
        val message = "Error fetching users"

        coEvery { getPaginatedUsersUseCase.getNextUsers(any()) } returns Result.failure(Exception(message))

        initViewModel()

        coVerify { getPaginatedUsersUseCase.getNextUsers(15) }

        assertThat(viewModel.screenState.value.users.size).isEqualTo(0)
        assertThat(viewModel.screenState.value.error).isEqualTo(message)
    }
}