package com.dilip.android.mobiquity_weather_app.viewmodels

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity
import com.dilip.android.mobiquity_weather_app.netwrork.responses.WeatherResponse
import com.dilip.android.mobiquity_weather_app.repositories.LocationRepository
import com.dilip.android.mobiquity_weather_app.utils.NetworkUtils
import com.dilip.android.mobiquity_weather_app.utils.SingleLiveData
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

@SuppressLint("CheckResult")
class MainViewModel : ViewModel() {

    private val mBackClicked = SingleLiveData<Boolean>()
    val backClicked: LiveData<Boolean> get() = mBackClicked

    private val mNavigateUp = SingleLiveData<Boolean>()
    val navigateUp: LiveData<Boolean> get() = mNavigateUp

    private val mLocations = SingleLiveData<List<LocationEntity>>()
    val locations: LiveData<List<LocationEntity>> get() = mLocations

    private val mLoader = SingleLiveData<Boolean>()
    val handleLoader: LiveData<Boolean> get() = mLoader

    private val mOneDayWeatherForecast = SingleLiveData<WeatherResponse>()
    val handleOneDayWeatherForecast: LiveData<WeatherResponse> get() = mOneDayWeatherForecast

    private val mNetworkUnavailable = SingleLiveData<Boolean>()
    val handleNetworkUnAvailable: LiveData<Boolean> get() = mNetworkUnavailable

    private lateinit var locationRepository: LocationRepository

    fun init(context: Context) {
        locationRepository = LocationRepository(context)
        subscribeToLocations()
    }

    fun savePlaceInDb(entity: LocationEntity) {
        locationRepository.saveLocation(entity)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                mBackClicked.value = true
            }, { Log.e("Error", it.message ?: "") })
    }

    private fun subscribeToLocations() {
        mLoader.value = true
        locationRepository.getLocations()
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe {
                mLoader.value = false
                mLocations.value = it
            }
    }

    fun deleteLocation(entity: LocationEntity) {
        locationRepository.deleteLocation(entity)
            ?.subscribeOn(Schedulers.io())
            ?.observeOn(AndroidSchedulers.mainThread())
            ?.subscribe({
                mLocations.value = mLocations.value?.toMutableList()?.apply {
                    remove(entity)
                }?.toList()
            }, { Log.e("Error", it.message ?: "") })
    }

    fun getWeatherResponse(context: Context, entity: LocationEntity) {
        if (!NetworkUtils.isNetworkAvailable(context)) {
            mNetworkUnavailable.value = true
            return
        }
        mLoader.value = true
        locationRepository.getWeather(entity)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<WeatherResponse?> {
                override fun onSuccess(response: WeatherResponse) {
                    mLoader.value = false
                    mOneDayWeatherForecast.value = response
                    Log.e("WeatherResponse", "->$response")
                }

                override fun onSubscribe(d: Disposable) {}

                override fun onError(e: Throwable) {
                    Log.e("Error", e.message ?: "")
                }

            })
    }
    fun performBackClick() {
        mBackClicked.value = true
    }
}