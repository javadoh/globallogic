package com.globallogic.homelist.data.mapper

interface IHomeListDomainMapper<T, E> {
    fun mapFrom(input: E?): T?
    fun mapTo(input: T?): E?
}