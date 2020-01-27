package com.globallogic.homelist

import com.globallogic.homelist.presentation.model.HomeList

interface IHomeListNavigation {
        fun navigateToDetail(feedArticle: HomeList)
}