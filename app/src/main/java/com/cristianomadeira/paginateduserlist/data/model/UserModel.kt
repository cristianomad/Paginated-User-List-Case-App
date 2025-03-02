package com.cristianomadeira.paginateduserlist.data.model

data class UserModel(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val thumbnail: String,
    val page: Int = -1
)