package com.cristianomadeira.paginateduserlist.domain.useCase

import androidx.annotation.VisibleForTesting
import com.cristianomadeira.paginateduserlist.domain.di.CoroutinesDispatchers
import com.cristianomadeira.paginateduserlist.domain.di.Dispatcher
import com.cristianomadeira.paginateduserlist.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

private const val PAGE_SIZE = 15

class GetPaginatedUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @Dispatcher(CoroutinesDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {
    @VisibleForTesting
    var page: Int = 1
        private set

    suspend fun getNextUsers() = withContext(dispatcher) {
        userRepository.getPaginatedUsers(page, PAGE_SIZE).fold(
            onSuccess = { users ->
                if (users.size == PAGE_SIZE) page++
                Result.success(users)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}