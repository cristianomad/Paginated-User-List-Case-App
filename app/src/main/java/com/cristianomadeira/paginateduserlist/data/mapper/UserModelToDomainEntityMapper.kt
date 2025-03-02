package com.cristianomadeira.paginateduserlist.data.mapper

import com.cristianomadeira.paginateduserlist.data.model.UserModel
import com.cristianomadeira.paginateduserlist.domain.entity.User
import com.cristianomadeira.paginateduserlist.util.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserModelToDomainEntityMapper @Inject constructor(): BaseMapper<UserModel, User>() {
    override fun mapFrom(from: UserModel): User =
        User(
            id = from.id,
            email = from.email,
            firstName = from.firstName,
            lastName = from.lastName,
            thumbnail = from.thumbnail
        )
}