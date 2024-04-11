package com.cristianomadeira.paginateduserlist.data.di

import com.cristianomadeira.paginateduserlist.data.dataSource.local.UserLocalDataSource
import com.cristianomadeira.paginateduserlist.data.dataSource.local.UserLocalDataSourceImpl
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.UserRemoteDataSource
import com.cristianomadeira.paginateduserlist.data.dataSource.remote.UserRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Binds
    fun bindUserRemoteDataSource(
        userRemoteDataSource: UserRemoteDataSourceImpl
    ): UserRemoteDataSource

    @Binds
    fun bindUserLocalDataSource(
        userLocalDataSource: UserLocalDataSourceImpl
    ): UserLocalDataSource
}