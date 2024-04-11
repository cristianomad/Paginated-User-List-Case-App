package com.cristianomadeira.paginateduserlist.data.dataSource.local.mapper

import com.cristianomadeira.paginateduserlist.data.dataSource.local.entity.UserEntity
import com.cristianomadeira.paginateduserlist.data.model.UserModel
import com.cristianomadeira.paginateduserlist.util.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserEntityToModelMapper @Inject constructor(): BaseMapper<UserEntity, UserModel>() {
    override fun mapFrom(from: UserEntity): UserModel =
        UserModel(
            id = from.id,
            email = from.email,
            firstName = from.firstName,
            lastName = from.lastName,
            thumbnail = from.thumbnail,
            page = from.page
        )
}