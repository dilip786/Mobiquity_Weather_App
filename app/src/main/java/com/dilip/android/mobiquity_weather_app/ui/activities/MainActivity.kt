package com.dilip.android.mobiquity_weather_app.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.dilip.android.mobiquity_weather_app.R
import com.dilip.android.mobiquity_weather_app.viewmodels.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initViewModel()
        initObservers()
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.init(this)
    }

    private fun initObservers() {
        viewModel.backClicked.observe(this, {
            handleBackClick()
        })
    }

    private fun handleBackClick() {
        val navigatedBack = Navigation.findNavController(this, R.id.nav_host_main_activity).navigateUp()
        if (!navigatedBack)
            finish()
    }

}