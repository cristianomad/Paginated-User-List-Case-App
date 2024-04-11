package com.cristianomadeira.paginateduserlist.data.dataSource.local

import com.cristianomadeira.paginateduserlist.data.model.UserModel

interface UserLocalDataSource {
    suspend fun getPaginatedUsers(page: Int): List<UserModel>

    suspend fun insertUsers(users: List<UserModel>)

    suspend fun deleteUsers()
}