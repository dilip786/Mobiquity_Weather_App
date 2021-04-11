package com.dilip.android.mobiquity_weather_app.db.entities


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

object LocationEntityConstants {
    const val TABLE_NAME = "locations"
    const val UUID = "uuid"
    const val NAME = "name"
    const val LATITUDE = "latitude"
    const val LONGITUDE = "longitude"
    const val CREATED_AT = "created_at"
}

@Entity(tableName = LocationEntityConstants.TABLE_NAME)
data class LocationEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = LocationEntityConstants.UUID)
    var uuid: String,

    @ColumnInfo(name = LocationEntityConstants.NAME)
    var locationName: String? = null,

    @ColumnInfo(name = LocationEntityConstants.LATITUDE)
    var latitude: Double? = null,

    @ColumnInfo(name = LocationEntityConstants.LONGITUDE)
    var longitude: Double? = null,

    @ColumnInfo(name = LocationEntityConstants.CREATED_AT)
    var createdAt: Long? = null,
)