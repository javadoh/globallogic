package com.globallogic.homelist.domain.entities

data class HomeListDataEntity(
    val title: String,
    val description: String? = null,
    val image: String? = null
)