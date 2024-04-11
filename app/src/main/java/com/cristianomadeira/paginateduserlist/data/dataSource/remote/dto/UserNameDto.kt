package com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class UserNameDto(
    val first: String,
    val last: String
)