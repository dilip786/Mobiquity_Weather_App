package com.dilip.android.mobiquity_weather_app.db.daos

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update
import io.reactivex.Single

interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(obj: T): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSingle(obj: T): Single<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList(obj: List<T>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListAsSingle(obj: MutableList<T>): Single<List<Long>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertListAsSingleIgnore(obj: MutableList<T>): Single<List<Long>>

    @Update
    fun update(obj: T): Single<Int>

    @Update
    fun updateList(obj: MutableList<T>): Int

    @Delete
    fun delete(obj: T): Single<Int>

    @Delete
    fun deleteList(obj: MutableList<T>): Single<Int>

}