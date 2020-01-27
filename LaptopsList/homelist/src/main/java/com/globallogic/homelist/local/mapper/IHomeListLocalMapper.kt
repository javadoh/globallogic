package com.globallogic.homelist.local.mapper

interface IHomeListLocalMapper<T, E> {
    fun mapFrom(input: E?): T?
    fun mapTo(input: T?): E?
}