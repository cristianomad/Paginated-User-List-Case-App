package com.cristianomadeira.paginateduserlist.data.model

data class UserModel(
    val id: String,
    val email: String,
    val firstName: String,
    val lastName: String,
    val thumbnail: String,
    val page: Int = -1
)