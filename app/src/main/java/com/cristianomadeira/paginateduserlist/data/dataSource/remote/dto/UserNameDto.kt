package com.cristianomadeira.paginateduserlist.data.dataSource.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserNameDto(
    @SerialName("first")
    val first: String,
    @SerialName("last")
    val last: String
)