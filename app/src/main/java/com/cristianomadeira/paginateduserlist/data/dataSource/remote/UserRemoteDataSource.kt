package com.cristianomadeira.paginateduserlist.data.dataSource.remote

import com.cristianomadeira.paginateduserlist.data.model.UserModel

interface UserRemoteDataSource {
    suspend fun getPaginatedUsers(page: Int, size: Int): Result<List<UserModel>>
}