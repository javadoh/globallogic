package com.globallogic.homelist.ui.adapters

import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.imageloader.utils.IImageLoader

internal class ItemViewBinder(private val imageLoader: IImageLoader,
                              private val onItemClickListener: OnItemClickListener) :
    AdapterViewBinder<HomeListAdapter.ItemVH, HomeList>() {

    override fun bind(viewHolder: HomeListAdapter.ItemVH, item: HomeList) {
        viewHolder.title.text = item.title

        imageLoader.loadItemImage(viewHolder.image, item.image)

        viewHolder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }

        viewHolder.description.text = item.description
    }
}