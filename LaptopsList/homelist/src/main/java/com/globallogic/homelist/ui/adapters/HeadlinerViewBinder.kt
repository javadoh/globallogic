package com.globallogic.homelist.ui.adapters

import com.globallogic.homelist.ui.adapters.HomeListAdapter.*
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.imageloader.utils.IImageLoader

internal class HeadlinerViewBinder(private val imageLoader: IImageLoader,
                                   private val onItemClickListener: OnItemClickListener) :
    AdapterViewBinder<HeadlinerVH, HomeList>() {

    override fun bind(viewHolder: HeadlinerVH, item: HomeList) {
        viewHolder.title.text = item.title

        imageLoader.loadItemImage(viewHolder.image, item.image)
        viewHolder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(item)
        }

        viewHolder.description.text = item.description
    }
}