package com.cristianomadeira.paginateduserlist.ui.feature.users

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.ui.component.Loading
import com.cristianomadeira.paginateduserlist.ui.component.NoUsersFound
import com.cristianomadeira.paginateduserlist.ui.component.UserCell
import com.cristianomadeira.paginateduserlist.ui.theme.SnackbarError
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

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
    val snackBarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(lazyListState.canScrollForward) {
        if (lazyListState.canScrollForward.not() && state.isLoading.not()) {
            getNextUsers()
        }
    }

    LaunchedEffect(state.error) {
        if (state.error.isNotBlank()) {
            coroutineScope.launch {
                snackBarHostState.showSnackbar(state.error)
            }
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState) { snackbarData ->
                Snackbar(
                    snackbarData = snackbarData,
                    containerColor = SnackbarError,
                    contentColor = White
                )
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
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

            if (state.isLoading && state.users.isEmpty()) {
                Loading()
            }

            if (state.users.isEmpty() && state.isLoading.not()) {
                NoUsersFound()
            }
        }
    }
}

@Preview(showBackground = true)

@Composable
fun UsersScreenPreview() {
    UsersScreenContent(
        state = UsersScreenState(
            users = listOf(
                User(id = 1, "john@email.com", "John", "Doe", ""),
                User(id = 1,"jane@email.com", "Jane", "Doe", "")
            ).toImmutableList()
        )
    )
}