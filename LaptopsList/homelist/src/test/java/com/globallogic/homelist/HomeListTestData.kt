package com.globallogic.homelist

import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.local.model.HomeListLocalData
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.homelist.remote.model.HomeListModelApi

object HomeListTestData {
        fun feed() = listOf(
            HomeList("Laptop 1", "hjgjhghjghjghjghjghjghjghj", ""),
            HomeList("Laptop 2", "asdasfdsfgdfkgjdfgds", "")
        )

        fun getArticle() = HomeListModelApi("nuevo titulo", "nueva descripcion", "imagen")

        fun getArticleResponse(articles: ArrayList<HomeListModelApi>) =
            arrayListOf(HomeListModelApi("nuevo titulo", "nueva descripcion", "imagen"))

        fun feedArticlesList() = listOf(
            HomeListDataEntity("un titulo", "b", "c"),
            HomeListDataEntity("otro titulo", "b", "c")
        )

        fun getFeedArticleData() = listOf(
            HomeListData("un titulo", "id_data_1"),
            HomeListData("otro titulo", "id_data_2")
        )

        fun getLocalFeed() =
            listOf(
                HomeListLocalData(
                    "titulo nuevo", "titulo Nuevo", "newsTitle"
                )
            )
}