package com.cristianomadeira.paginateduserlist.domain.entity

data class User(
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val thumbnail: String
)