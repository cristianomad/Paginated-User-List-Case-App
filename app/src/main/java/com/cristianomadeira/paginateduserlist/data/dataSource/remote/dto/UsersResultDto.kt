package com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UsersResultDto(
    val results: List<UserDto>
)