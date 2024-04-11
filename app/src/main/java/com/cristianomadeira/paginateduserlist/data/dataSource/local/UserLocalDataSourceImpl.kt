package com.cristianomadeira.paginateduserlist.data.dataSource.local

import com.cristianomadeira.paginateduserlist.data.dataSource.local.database.AppDatabase
import com.cristianomadeira.paginateduserlist.data.dataSource.local.mapper.UserEntityToModelMapper
import com.cristianomadeira.paginateduserlist.data.dataSource.local.mapper.UserModelToEntityMapper
import com.cristianomadeira.paginateduserlist.data.model.UserModel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserLocalDataSourceImpl @Inject constructor(
    private val database: AppDatabase,
    private val userEntityToModelMapper: UserEntityToModelMapper,
    private val userModelToEntityMapper: UserModelToEntityMapper
): UserLocalDataSource {
    override suspend fun getPaginatedUsers(page: Int): List<UserModel> =
        userEntityToModelMapper.mapFromList(database.userDao().getUsers(page))

    override suspend fun insertUsers(users: List<UserModel>) =
        database.userDao().insertUsers(userModelToEntityMapper.mapFromList(users))

    override suspend fun deleteUsers() = database.userDao().deleteUsers()
}