package com.cristianomadeira.paginateduserlist.ui.feature.users

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.domain.useCase.GetPaginatedUsersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getPaginatedUsersUseCase: GetPaginatedUsersUseCase
): ViewModel() {
    private val _screenState = MutableStateFlow(UsersScreenState())
    val screenState = _screenState.asStateFlow()

    private val currentScreenState =  { _screenState.value }

    init {
        getNextUsers()
    }

    fun getNextUsers() {
        viewModelScope.launch {
            setScreenState { copy(isLoading = true) }

            getPaginatedUsersUseCase.getNextUsers().fold(
                onSuccess = { users ->
                    setScreenState {
                        copy(
                            users = (currentScreenState().users + users).toImmutableList(),
                            isLoading = false
                        )
                    }
                },
                onFailure = { error ->
                    setScreenState { copy(isLoading = false) }
                    error.message?.let { setError(it) }
                }
            )
        }
    }

    private fun setScreenState(reduce: UsersScreenState.() -> UsersScreenState) {
        _screenState.value = _screenState.value.reduce()
    }

    private suspend fun setError(message: String) {
        setScreenState { copy(error = message) }
        delay(4000)
        setScreenState { copy(error = "") }
    }
}

data class UsersScreenState(
    val isLoading: Boolean = false,
    val users: ImmutableList<User> = emptyList<User>().toImmutableList(),
    val error: String = ""
)