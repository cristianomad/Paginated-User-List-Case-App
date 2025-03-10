package com.cristianomadeira.paginateduserlist.data.dataSource.remote.mapper

import com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto.UserDto
import com.cristianomadeira.paginateduserlist.data.model.UserModel
import com.cristianomadeira.paginateduserlist.util.BaseMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDtoToModelMapper @Inject constructor(): BaseMapper<UserDto, UserModel>() {
    override fun mapFrom(from: UserDto): UserModel =
        UserModel(
            id  = from.id,
            email = from.email,
            firstName = from.firstName,
            lastName = from.lastName,
            thumbnail = from.thumbnail
        )
}