package com.dilip.android.mobiquity_weather_app.db.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dilip.android.mobiquity_weather_app.db.daos.LocationDao
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity

@Database(entities = [LocationEntity::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun locationDao(): LocationDao

    companion object {
        private var INSTANCE: WeatherDatabase? = null

        fun getInstance(context: Context): WeatherDatabase? {
            if (INSTANCE == null) {
                synchronized(WeatherDatabase::class) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        WeatherDatabase::class.java, "WeatherDatabase")
                        .build()
                }
            }
            return INSTANCE
        }
    }
}