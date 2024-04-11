package com.cristianomadeira.paginateduserlist.util

abstract class BaseMapper<From, To> {

    abstract fun mapFrom(from: From): To

    fun mapFromList(from: List<From?>?) = from?.filterNotNull()?.map { mapFrom(it) } ?: emptyList()
}