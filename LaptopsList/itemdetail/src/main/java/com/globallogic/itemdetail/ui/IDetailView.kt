package com.globallogic.itemdetail.ui

import com.globallogic.itemdetail.presentation.model.ItemDetail

interface IDetailView {
        fun showDetail(article: ItemDetail)
}