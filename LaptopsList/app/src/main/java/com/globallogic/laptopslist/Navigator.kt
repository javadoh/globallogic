package com.globallogic.laptopslist

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.globallogic.homelist.IHomeListNavigation
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.itemdetail.presentation.model.ItemDetail
import com.globallogic.itemdetail.ui.DetailActivity
import com.globallogic.itemdetail.ui.EXTRA_DETAIL

class Navigator(private val context: Context) : IHomeListNavigation {

    override fun navigateToDetail(feedArticle: HomeList) {
        val intent = Intent(context, DetailActivity::class.java).apply {
            putExtra(EXTRA_DETAIL, mapFeedToDetail(feedArticle))
        }
        ContextCompat.startActivity(context, intent, null)
    }

    private fun mapFeedToDetail(input: HomeList) =
        ItemDetail(
            input.title,
            input.description ?: "",
            input.image ?: ""
        )
}