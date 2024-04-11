package com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    val name: UserNameDto,
    val email: String,
    val picture: UserPictureDto
)