/*
 * Developed by Makis Kontis on 17/03/21 11:12.
 * Last modified 17/03/21 11:12.
 * Copyright (c) TouchNote Ltd 2021. All rights reserved.
 */

package com.dilip.android.mobiquity_weather_app.repositories

import ApiInterface
import android.content.Context
import com.dilip.android.mobiquity_weather_app.db.database.WeatherDatabase
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity
import com.dilip.android.mobiquity_weather_app.netwrork.responses.WeatherResponse
import com.dilip.android.mobiquity_weather_app.netwrork.ApiClient
import io.reactivex.Flowable
import io.reactivex.Single

class LocationRepository(context: Context) {
    private var database: WeatherDatabase? = null

    init {
        database = WeatherDatabase.getInstance(context)
    }

    fun saveLocation(entity: LocationEntity): Single<Long>? {
        return database?.locationDao()?.insertSingle(entity)
    }

    fun getLocations(): Flowable<List<LocationEntity>>? {
        return database?.locationDao()?.getLocations()
    }

    fun deleteLocation(entity: LocationEntity): Single<Int>? {
        return database?.locationDao()?.delete(entity)
    }

    fun getWeather(entity: LocationEntity): Single<WeatherResponse> {
        return ApiClient().getClient()!!.create(ApiInterface::class.java)
            .getWeatherData(entity.latitude.toString(), entity.longitude.toString(), "fae7190d7e6433ec3a45285ffcf55c86")
    }
}