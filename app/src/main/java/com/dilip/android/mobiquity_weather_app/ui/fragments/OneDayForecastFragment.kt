package com.dilip.android.mobiquity_weather_app.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dilip.android.mobiquity_weather_app.R
import com.dilip.android.mobiquity_weather_app.netwrork.responses.WeatherResponse
import com.dilip.android.mobiquity_weather_app.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.one_day_forecast.*
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SetTextI18n")
class OneDayForecastFragment : Fragment() {

    companion object {
        const val BUNDLE_DATA = "bundle_data"
    }

    private lateinit var viewModel: MainViewModel
    private var bundleData: WeatherResponse? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.one_day_forecast, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        bundleData = arguments?.getSerializable(BUNDLE_DATA) as WeatherResponse?
        setViewData()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init(requireActivity())
    }

    private fun initObservers() {}

    private fun setViewData() {
        bundleData?.let {
            tvLocationName.text = it.name
            tvHumidity.text = "Humidity ${it.main?.humidity}%"
            tvPressure.text ="Pressure ${it.main?.pressure}"
            tvWind.text ="Speed ${it.wind?.speed} m/s"
            tvDate.text =SimpleDateFormat("MMM dd,yyyy", Locale.US).format(Calendar.getInstance().time)
            tvTemperature.text = "${String.format("%.2f", it.main?.temp?.minus(273.15F))}°C"
        }
    }
}