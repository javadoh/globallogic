package com.globallogic.itemdetail.presentation.presenters

import com.globallogic.itemdetail.presentation.model.ItemDetail
import com.globallogic.itemdetail.ui.IDetailView

interface Presenter <V : IDetailView> {
        fun startPresenting(article: ItemDetail)
    }