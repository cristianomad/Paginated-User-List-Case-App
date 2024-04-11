package com.cristianomadeira.paginateduserlist.data.dataSource.local.mapper

import com.cristianomadeira.paginateduserlist.data.dataSource.local.entity.UserEntity
import com.cristianomadeira.paginateduserlist.data.model.UserModel
import com.cristianomadeira.paginateduserlist.util.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserModelToEntityMapper @Inject constructor(): BaseMapper<UserModel, UserEntity>() {
    override fun mapFrom(from: UserModel): UserEntity =
        UserEntity(
            email = from.email,
            firstName = from.firstName,
            lastName = from.lastName,
            thumbnail = from.thumbnail,
            page = from.page
        )
}