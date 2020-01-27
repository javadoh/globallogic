package com.globallogic.homelist.remote.model

        data class HomeListModelApi(
                val title: String,
                val description: String? = null,
                val image: String? = null
        )