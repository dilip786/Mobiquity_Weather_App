package com.dilip.android.mobiquity_weather_app.netwrork

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient {
    private val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    private var retrofit: Retrofit? = null
    private var logging: HttpLoggingInterceptor? = null
    private var httpClientBuilder: OkHttpClient.Builder? = null

    fun getClient(): Retrofit? {
        if (retrofit == null) {
            logging = HttpLoggingInterceptor()
            logging?.setLevel(HttpLoggingInterceptor.Level.BODY)
            httpClientBuilder = OkHttpClient.Builder()
            httpClientBuilder?.addInterceptor(logging!!)

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClientBuilder?.build())
                .build()
        }
        return retrofit
    }
}