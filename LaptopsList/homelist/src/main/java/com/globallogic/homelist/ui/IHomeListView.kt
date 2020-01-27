package com.globallogic.homelist.ui

import com.globallogic.homelist.presentation.model.HomeList

interface IHomeListView {
    fun showList(show: Boolean, dataset: List<HomeList?>? = null)
    fun showError(show: Boolean, message: String? = null)
    fun showLoading(show: Boolean)
}