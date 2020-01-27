package com.globallogic.homelist.presentation.mapper

interface IHomeListMapper<T, E> {

    fun mapFrom(input: List<E?>): List<T?>
    fun mapTo(input: List<T?>): List<E?>
}