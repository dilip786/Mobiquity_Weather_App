import com.dilip.android.mobiquity_weather_app.netwrork.responses.WeatherResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query


interface ApiInterface {
    @Headers("Content-Type: application/json")
    @GET("weather?")
    fun getWeatherData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") app_id: String
    ): Single<WeatherResponse>
}