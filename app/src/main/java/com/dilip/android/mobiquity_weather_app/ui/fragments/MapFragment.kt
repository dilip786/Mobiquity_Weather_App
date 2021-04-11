package com.dilip.android.mobiquity_weather_app.ui.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.dilip.android.mobiquity_weather_app.R
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity
import com.dilip.android.mobiquity_weather_app.ui.dialogs.AddPlaceNameDialog
import com.dilip.android.mobiquity_weather_app.viewmodels.MainViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place.Field
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import kotlinx.android.synthetic.main.maps_fragment.*
import java.util.*

class MapFragment : Fragment(), OnMapReadyCallback, GoogleMap.OnCameraIdleListener {

    private val AUTO_PLACE_SEARCH_ID: Int = 102
    private lateinit var mMap: GoogleMap
    private lateinit var mapFragment: SupportMapFragment
    private var midLatLng: LatLng? = null
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.maps_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        if (!Places.isInitialized())
            Places.initialize(requireActivity(), "AIzaSyDdlVQM2QH72Zqgeep38QJWN9xeT76qIto")
        initListener()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.setOnCameraIdleListener(this)
        updateLocation(17.3850, 78.4867)
    }

    override fun onCameraIdle() {
        midLatLng = mMap.cameraPosition.target
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        viewModel.init(requireActivity())
    }

    private fun initListener() {
        clHeader.setOnClickListener {
            moveToSearchAddressActivity()
        }
        btnAddMoreDetails.setOnClickListener {
            AddPlaceNameDialog(requireContext()) {
                viewModel.savePlaceInDb(
                    LocationEntity(
                        uuid = UUID.randomUUID().toString(),
                        locationName = it,
                        latitude = midLatLng?.latitude,
                        longitude = midLatLng?.longitude,
                        createdAt = System.currentTimeMillis()
                    )
                )
            }.show()
        }
    }

    private fun moveToSearchAddressActivity() {
        val fields = arrayListOf(Field.ID, Field.NAME, Field.LAT_LNG, Field.ADDRESS)
        val intent: Intent?
        intent =
            Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields).setCountry("IN")
                .build(requireActivity())
        startActivityForResult(intent, AUTO_PLACE_SEARCH_ID)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            AUTO_PLACE_SEARCH_ID -> {
                if (resultCode == Activity.RESULT_OK) {
                    val place = data?.let { Autocomplete.getPlaceFromIntent(it) }
                    updateLocation(place?.latLng?.latitude!!, place.latLng?.longitude!!)
                }
            }
        }
    }

    private fun updateLocation(latitude: Double, longitude: Double) {
        midLatLng = LatLng(latitude, longitude)
        val yourLocation = CameraUpdateFactory.newLatLngZoom(midLatLng, 17f)
        mMap.moveCamera(yourLocation)
    }
}