package com.cristianomadeira.paginateduserlist.domain.repository

import com.cristianomadeira.paginateduserlist.domain.entity.User

interface UserRepository {
    suspend fun getPaginatedUsers(page: Int, size: Int): Result<List<User>>
}