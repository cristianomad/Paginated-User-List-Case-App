package com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDto(
    @SerialName("login")
    val login: LoginDto,
    @SerialName("name")
    val name: UserNameDto,
    @SerialName("email")
    val email: String,
    @SerialName("picture")
    val picture: UserPictureDto
)