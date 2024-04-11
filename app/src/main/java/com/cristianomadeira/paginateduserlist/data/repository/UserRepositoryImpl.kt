package com.cristianomadeira.paginateduserlist.data.repository

import com.cristianomadeira.paginateduserlist.data.dataSource.local.UserLocalDataSource
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.UserRemoteDataSource
import com.cristianomadeira.paginateduserlist.data.mapper.UserModelToDomainEntityMapper
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.domain.repository.UserRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userRemoteDataSource: UserRemoteDataSource,
    private val userLocalDataSource: UserLocalDataSource,
    private val userModelToDomainEntityMapper: UserModelToDomainEntityMapper
): UserRepository {
    override suspend fun getPaginatedUsers(page: Int, size: Int): Result<List<User>> =
        userRemoteDataSource.getPaginatedUsers(page, size).fold(
            onSuccess = { remoteResult ->
                val users = remoteResult.map { user -> user.copy(page = page) }

                if (page == 1) userLocalDataSource.deleteUsers()

                userLocalDataSource.insertUsers(users)

                val result = userModelToDomainEntityMapper.mapFromList(
                    userLocalDataSource.getPaginatedUsers(page)
                )

                Result.success(result)
            },
            onFailure = {
                val localResult = userModelToDomainEntityMapper.mapFromList(
                    userLocalDataSource.getPaginatedUsers(page)
                )

                when {
                    localResult.isNotEmpty() -> Result.success(localResult)
                    else -> Result.failure(
                        Exception("Could not fetch users. Check your internet connection and try again.")
                    )
                }
            }
        )
}