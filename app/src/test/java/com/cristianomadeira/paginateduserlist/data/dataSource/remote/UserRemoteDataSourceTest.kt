package com.cristianomadeira.paginateduserlist.data.dataSource.remote

import com.cristianomadeira.paginateduserlist.data.dataSource.remote.client.HttpClientProvider
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto.UsersResultDto
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.mapper.UserDtoToModelMapper
import com.google.common.truth.Truth.assertThat
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.headersOf
import io.ktor.utils.io.ByteReadChannel
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Test

class UserRemoteDataSourceTest {
    @Test
    fun `when fetch users, successfully parse api response`() {
        val expected = Json { ignoreUnknownKeys = true }.decodeFromString<UsersResultDto>(
            API_RESPONSE
        )

        val mockEngine = MockEngine {
            respond(
                content = ByteReadChannel(API_RESPONSE),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }

        runTest {
            val httpClient = HttpClientProvider()(mockEngine)
            val dataSource = UserRemoteDataSourceImpl(httpClient, UserDtoToModelMapper())
            val response = dataSource.getPaginatedUsers(page = 1, size = 3)

            response.onSuccess { result ->
                assertThat(result).isEqualTo(expected)
            }
        }
    }
}