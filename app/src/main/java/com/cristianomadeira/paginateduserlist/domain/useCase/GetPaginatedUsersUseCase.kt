package com.cristianomadeira.paginateduserlist.domain.useCase

import androidx.annotation.VisibleForTesting
import com.cristianomadeira.paginateduserlist.domain.di.CoroutinesDispatchers
import com.cristianomadeira.paginateduserlist.domain.di.Dispatcher
import com.cristianomadeira.paginateduserlist.domain.repository.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetPaginatedUsersUseCase @Inject constructor(
    private val userRepository: UserRepository,
    @Dispatcher(CoroutinesDispatchers.IO) private val dispatcher: CoroutineDispatcher
) {
    @VisibleForTesting
    var page: Int = 1
        private set

    suspend fun getNextUsers(size: Int) = withContext(dispatcher) {
        userRepository.getPaginatedUsers(page, size).fold(
            onSuccess = { users ->
                if (users.size == size) page++
                Result.success(users)
            },
            onFailure = { error ->
                Result.failure(error)
            }
        )
    }
}