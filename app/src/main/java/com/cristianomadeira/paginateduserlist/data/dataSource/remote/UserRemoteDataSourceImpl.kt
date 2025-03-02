package com.cristianomadeira.paginateduserlist.data.dataSource.remote

import com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto.UsersResultDto
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.mapper.UserDtoToModelMapper
import com.cristianomadeira.paginateduserlist.data.model.UserModel
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSourceImpl @Inject constructor(
    private val client: HttpClient,
    private val userDtoToModelMapper: UserDtoToModelMapper
): UserRemoteDataSource {
    override suspend fun getPaginatedUsers(page: Int, size: Int): Result<List<UserModel>> = runCatching {
        val response: UsersResultDto = client.get("?skip=${page * size}&limit=$size").body()
        userDtoToModelMapper.mapFromList(response.results)
    }
}