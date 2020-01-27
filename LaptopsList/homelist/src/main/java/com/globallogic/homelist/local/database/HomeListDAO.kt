package com.globallogic.homelist.local.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.globallogic.homelist.local.model.HomeListLocalData
import io.reactivex.Completable
import io.reactivex.Observable

@Dao
interface HomeListDAO {
    @Query("SELECT * FROM home_list_local_data")
    fun getHomeList(): Observable<List<HomeListLocalData>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveHomeListItem(homeListItem: HomeListLocalData)

    @Query("DELETE from home_list_local_data")
    fun clearHomeList(): Completable

    @Query("DELETE from home_list_local_data WHERE title = :idToRemove")
    fun removeHomeListItem(idToRemove: String): Completable
}