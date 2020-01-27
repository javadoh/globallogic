package com.globallogic.homelist.presentation.mapper

import com.globallogic.homelist.domain.entities.HomeListDataEntity
import com.globallogic.homelist.presentation.model.HomeList

internal class HomeListDomainItemsMapper :
    IHomeListMapper<HomeList?, HomeListDataEntity?> {

    override fun mapFrom(input: List<HomeListDataEntity?>): List<HomeList?> {
        val result = ArrayList<HomeList>()

        input.filterNotNull().forEach {
            result.add(
                HomeList(
                    it.title,
                    it.description,
                    it.image
                )
            )
        }
        return result
    }


    override fun mapTo(input: List<HomeList?>): List<HomeListDataEntity?> {
        val result = ArrayList<HomeListDataEntity>()

        input.filterNotNull().forEach {
            result.add(
                HomeListDataEntity(
                    it.title,
                    it.description,
                    it.image
                )
            )
        }
        return result
    }
}