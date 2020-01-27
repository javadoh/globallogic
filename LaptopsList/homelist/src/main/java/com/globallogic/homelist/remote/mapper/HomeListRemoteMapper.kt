package com.globallogic.homelist.remote.mapper

interface HomeListRemoteMapper<T, E> {
        fun mapFrom(input: E?): T?
        fun mapTo(input: T?): E?
    }