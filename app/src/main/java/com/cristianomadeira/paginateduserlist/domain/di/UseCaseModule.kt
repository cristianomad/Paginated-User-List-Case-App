package com.cristianomadeira.paginateduserlist.domain.di

import com.cristianomadeira.paginateduserlist.domain.repository.UserRepository
import com.cristianomadeira.paginateduserlist.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
interface UseCaseModule {

    @Binds
    fun bindsUserRepository(
        userRepository: UserRepositoryImpl
    ): UserRepository
}