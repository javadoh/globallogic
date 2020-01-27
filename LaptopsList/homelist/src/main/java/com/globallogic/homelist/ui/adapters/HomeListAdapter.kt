package com.globallogic.homelist.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.globallogic.homelist.R
import com.globallogic.homelist.presentation.model.HomeList

private const val TYPE_HEADLINER = 0
private const val TYPE_ITEM = 1

internal class HomeListAdapter(
    private val headlinerViewBinder: HeadlinerViewBinder,
    private val itemViewBinder: ItemViewBinder,
    private val dataset: List<HomeList?>?
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADLINER -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.homelist_headliner, parent, false) as View

                HeadlinerVH(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.homelist_item, parent, false) as View

                ItemVH(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        dataset?.get(position)?.let {
            when (getItemViewType(position)) {
                TYPE_HEADLINER -> {
                    headlinerViewBinder.bind(holder as HeadlinerVH, it)
                }
                else -> {
                    itemViewBinder.bind(holder as ItemVH, it)
                }
            }
        }
    }

    override fun getItemCount() = dataset?.size ?: 0

    override fun getItemViewType(position: Int) = when (position) {
        0 -> TYPE_HEADLINER
        else -> TYPE_ITEM
    }

    class ItemVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val image: ImageView = view.findViewById(R.id.item_image)
        val description: TextView = view.findViewById(R.id.item_description)
    }

    class HeadlinerVH(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.item_title)
        val image: ImageView = view.findViewById(R.id.item_image)
        val description: TextView = view.findViewById(R.id.item_description)
    }
}