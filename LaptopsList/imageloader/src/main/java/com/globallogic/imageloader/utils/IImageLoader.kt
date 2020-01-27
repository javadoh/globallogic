package com.globallogic.imageloader.utils

import android.widget.ImageView

interface IImageLoader {
    //fun loadHeadlinerImage(imageView: ImageView, urlToImage: String?)
    fun loadItemImage(imageView: ImageView, image: String?)
}