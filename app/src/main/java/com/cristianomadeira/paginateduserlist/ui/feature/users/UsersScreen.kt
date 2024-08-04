package com.cristianomadeira.paginateduserlist.ui.feature.users

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.ui.component.ErrorSnackbar
import com.cristianomadeira.paginateduserlist.ui.component.Loading
import com.cristianomadeira.paginateduserlist.ui.component.NoUsersFound
import com.cristianomadeira.paginateduserlist.ui.component.UserCell
import kotlinx.collections.immutable.toImmutableList

@Composable
fun UsersScreen(
    viewModel: UsersViewModel = hiltViewModel()
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()

    UsersScreenContent(state, viewModel::getNextUsers)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun UsersScreenContent(
    state: UsersScreenState,
    getNextUsers: () -> Unit = {}
) {
    val lazyListState = rememberLazyListState()

    LaunchedEffect(lazyListState.canScrollForward) {
        if (lazyListState.canScrollForward.not() && state.isLoading.not()) {
            getNextUsers()
        }
    }

    Column {
        CompositionLocalProvider(
            // Disable overscroll effect
            LocalOverscrollConfiguration provides null
        ) {
            LazyColumn(state = lazyListState) {
                items(state.users) { user ->
                    UserCell(user)
                }

                if (state.isLoading && state.users.isNotEmpty()) {
                    item {
                        Loading(
                            modifier = Modifier
                                .padding(vertical = 16.dp)
                                .size(24.dp)
                        )
                    }
                }
            }
        }
    }

    Loading(visible = state.isLoading && state.users.isEmpty())

    ErrorSnackbar(
        errorMessage = state.error,
        showError = state.error.isNotBlank()
    )

    NoUsersFound(visible = state.users.isEmpty() && state.isLoading.not())
}

@Preview
@Composable
fun UsersScreenPreview() {
    UsersScreenContent(
        state = UsersScreenState(
            users = listOf(
                User("john@email.com", "John", "Doe", ""),
                User("jane@email.com", "Jane", "Doe", "")
            ).toImmutableList()
        )
    )
}