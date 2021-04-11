package com.dilip.android.mobiquity_weather_app.db.daos

import androidx.room.Dao
import androidx.room.Query
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntityConstants
import io.reactivex.Flowable


@Dao
abstract class LocationDao : BaseDao<LocationEntity> {
    @Query("SELECT * FROM ${LocationEntityConstants.TABLE_NAME} ORDER BY ${LocationEntityConstants.CREATED_AT} DESC")
    abstract fun getLocations(): Flowable<List<LocationEntity>>
}