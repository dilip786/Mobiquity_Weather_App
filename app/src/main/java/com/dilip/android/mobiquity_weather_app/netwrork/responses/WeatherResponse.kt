package com.dilip.android.mobiquity_weather_app.netwrork.responses

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class WeatherResponse(

    @SerializedName("weather")
    val weathers: List<Weather>? = null,

    @SerializedName("main")
    val main: Main? = null,

    @SerializedName("wind")
    val wind: Wind? = null,

    @SerializedName("name")
    val name: String? = null
) : Serializable {
    data class Weather(
        @SerializedName("main")
        val main: String? = null,

        @SerializedName("description")
        val description: String? = null,

        @SerializedName("icon")
        val icon: String? = null,
    )

    data class Main(
        @SerializedName("temp")
        val temp: Float? = null,

        @SerializedName("feels_like")
        val feelsLike: Float? = null,

        @SerializedName("temp_min")
        val tempMin: Float? = null,

        @SerializedName("temp_max")
        val tempMax: Float? = null,

        @SerializedName("pressure")
        val pressure: Int? = null,

        @SerializedName("humidity")
        val humidity: Int? = null,
    )

    data class Wind(
        @SerializedName("speed")
        val speed: Float? = null,

        @SerializedName("deg")
        val deg: Int? = null,
    )
}