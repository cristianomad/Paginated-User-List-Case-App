package com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UsersResultDto(
    @SerialName("results")
    val results: List<UserDto>
)