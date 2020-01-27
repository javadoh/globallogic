package com.globallogic.homelist.ui.adapters

import androidx.recyclerview.widget.RecyclerView

abstract class AdapterViewBinder<VH : RecyclerView.ViewHolder, I> {
    abstract fun bind(viewHolder: VH, item: I)
}