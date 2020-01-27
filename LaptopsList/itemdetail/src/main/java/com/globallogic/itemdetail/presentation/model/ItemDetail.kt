package com.globallogic.itemdetail.presentation.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ItemDetail(
    val title: String,
    val description: String,
    val image: String
) : Parcelable