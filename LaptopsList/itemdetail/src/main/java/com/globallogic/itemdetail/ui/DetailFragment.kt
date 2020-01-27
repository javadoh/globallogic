package com.globallogic.itemdetail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.globallogic.imageloader.utils.IImageLoader
import com.globallogic.itemdetail.R
import com.globallogic.itemdetail.presentation.model.ItemDetail
import com.globallogic.itemdetail.presentation.presenters.Presenter
import kotlinx.android.synthetic.main.detail_fragment.*
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

private const val ARG_FEED_ARTICLE = "arg_feed_article"

class DetailFragment : Fragment(), KoinComponent, IDetailView {

    private val imageLoaderUtil: IImageLoader by inject()

    private val presenter: Presenter<IDetailView> by inject {
        parametersOf(this)
    }

    companion object {
        fun newInstance(feed: ItemDetail) = DetailFragment().apply {
            arguments = bundleOf(Pair(ARG_FEED_ARTICLE, feed))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.detail_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.getParcelable<ItemDetail>(
            ARG_FEED_ARTICLE
        )?.let {
            presenter.startPresenting(it)
        }
    }

    override fun showDetail(article: ItemDetail) {
        article.apply {
            detail_title.text = this.title
            detail_description.text = this.description
            imageLoaderUtil.loadItemImage(detail_image, this.image)
        }
    }
}