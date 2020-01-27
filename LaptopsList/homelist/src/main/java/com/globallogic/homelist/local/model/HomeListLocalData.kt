package com.globallogic.homelist.local.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "home_list_local_data")
data class HomeListLocalData(
@PrimaryKey @ColumnInfo(name="title") val title: String,
    @ColumnInfo(name="description") val description: String? = null,
    @ColumnInfo(name="image") val image: String? = null
)