package com.globallogic.homelist.data.mapper

import com.globallogic.homelist.data.model.HomeListData
import com.globallogic.homelist.domain.entities.HomeListDataEntity

internal class HomeListDomainDataMapper :
    IHomeListDomainMapper<HomeListDataEntity, HomeListData> {

    override fun mapFrom(input: HomeListData?): HomeListDataEntity? {
        return input?.let {
            HomeListDataEntity(
                input.title,
                input.description,
                input.image
            )
        }
    }

    override fun mapTo(input: HomeListDataEntity?): HomeListData? {
        return input?.let {
            HomeListData(
                input.title,
                input.description,
                input.image
            )
        }
    }
}