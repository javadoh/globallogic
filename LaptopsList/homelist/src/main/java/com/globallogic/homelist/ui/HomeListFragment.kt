package com.globallogic.homelist.ui

import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.globallogic.homelist.IHomeListNavigation
import com.globallogic.homelist.R
import com.globallogic.homelist.presentation.model.HomeList
import com.globallogic.homelist.presentation.model.resource.ResourceStatusDelegate
import com.globallogic.homelist.presentation.viewmodel.HomeListVM
import com.globallogic.homelist.ui.adapters.OnItemClickListener
import kotlinx.android.synthetic.main.fragment_homelist.*
import kotlinx.android.synthetic.main.fragment_homelist_error.*
import kotlinx.android.synthetic.main.fragment_homelist_loading.*
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import org.koin.core.qualifier.named

class HomeListFragment : Fragment(), IHomeListView, OnItemClickListener {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>

    private val viewModel: HomeListVM by viewModel()
    private val resourceDelegate: ResourceStatusDelegate<List<HomeList?>> by inject {
        parametersOf(this)
    }

    private val navigation: IHomeListNavigation by inject {
        parametersOf(requireActivity())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_homelist, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        val dividerItemDecoration = DividerItemDecoration(
            recycler.context,
            layoutManager.orientation
        )
        dividerItemDecoration.setDrawable(getDividerAndIncreaseHeight())
        recycler.addItemDecoration(dividerItemDecoration)
        recycler.setHasFixedSize(true)
        recycler.layoutManager = layoutManager
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        this.viewModel.items.observe(this, Observer { resource ->
            resourceDelegate.handleStatus(resource)
        })
    }

    override fun onItemClick(item: HomeList) {
        navigation.navigateToDetail(item)
    }

    override fun showError(show: Boolean, message: String?) {
        if (show) {
            error.visibility = View.VISIBLE
            message?.let { error_tv.text = it }
        } else {
            error.visibility = View.GONE
        }
    }

    override fun showList(show: Boolean, dataset: List<HomeList?>?) {
        if (show) {
            recycler.visibility = View.VISIBLE
            viewAdapter = get(named("HomeListAdapter")) { parametersOf(this, dataset) }

            recycler.apply {
                adapter = viewAdapter
            }
        } else {
            recycler.visibility = View.GONE
        }
    }

    override fun showLoading(show: Boolean) {
        if (show) {
            loading.visibility = View.VISIBLE
        } else {
            loading.visibility = View.GONE
        }
    }

    private fun getDividerAndIncreaseHeight(): InsetDrawable {
        val attrs = intArrayOf(android.R.attr.listDivider)

        val a = requireActivity().obtainStyledAttributes(attrs)
        val divider = a.getDrawable(0)
        val insetV = resources.getDimensionPixelSize(R.dimen.feed_divider_inset_vertical)
        val insetH = resources.getDimensionPixelSize(R.dimen.feed_divider_inset_horizontal)
        a.recycle()

        return InsetDrawable(divider, insetH, insetV, insetH, insetV)
    }
}