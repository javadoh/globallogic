package com.globallogic.homelist.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
    @Parcelize
    data class HomeList(
        val title: String,
        val description: String? = null,
        val image: String? = null
    ) : Parcelable