package com.dilip.android.mobiquity_weather_app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dilip.android.mobiquity_weather_app.R
import com.dilip.android.mobiquity_weather_app.db.entities.LocationEntity
import com.dilip.android.mobiquity_weather_app.ui.adapters.LocationsAdapter
import com.dilip.android.mobiquity_weather_app.viewmodels.MainViewModel
import kotlinx.android.synthetic.main.locations_fragment.*

class HomeFragment : Fragment() {

    private lateinit var viewModel: MainViewModel
    private lateinit var locationsAdapter: LocationsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.locations_fragment, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.init(requireActivity())
        initObservers()
        initializeListeners()
        initRecyclerView()
    }

    private fun initializeListeners() {
        addLocation.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
        floatingAddLocation.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_mapFragment)
        }
    }

    private fun initRecyclerView() {
        rvLocations.layoutManager =
            LinearLayoutManager(requireActivity(), RecyclerView.VERTICAL, false)
        locationsAdapter = LocationsAdapter(context = requireContext(), onItemClickListener = {
            viewModel.getWeatherResponse(requireActivity(), it)
        }, onItemDeleteClickListener = {
            viewModel.deleteLocation(it)
        })
        rvLocations.adapter = locationsAdapter
    }

    private fun initViewModel() {
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
    }

    private fun initObservers() {
        viewModel.locations.observe(requireActivity(), {
            updateLocations(it)
        })

        viewModel.handleNetworkUnAvailable.observe(requireActivity(), {
            showNoNetworkDialog()
        })

        viewModel.handleOneDayWeatherForecast.observe(requireActivity(), {
            val bundle = bundleOf(OneDayForecastFragment.BUNDLE_DATA to it)
            findNavController().navigate(R.id.action_homeFragment_to_oneDayForecast,bundle)
        })

        viewModel.navigateUp.observe(requireActivity(), {
            Navigation.findNavController(requireActivity(), R.id.nav_host_main_activity).navigateUp()
        })

        viewModel.handleLoader.observe(requireActivity(), {
            handleLoader(it)
        })

    }

    private fun handleLoader(show: Boolean) {
        progressLayout.visibility = if (show) View.VISIBLE else View.GONE
    }

    private fun showNoNetworkDialog() {
        AlertDialog.Builder(requireContext(),R.style.CustomDialogTheme)
            .setTitle(resources.getString(R.string.whoops))
            .setMessage(resources.getString(R.string.no_internet))
            .setPositiveButton(resources.getString(R.string.okay)) { dialog, _ ->
                dialog.dismiss()
            }.show()
    }

    private fun updateLocations(list: List<LocationEntity>) {
        if (list.isNotEmpty()) {
            addLocation.visibility = View.GONE
            locationHint.visibility = View.GONE
            rvLocations.visibility = View.VISIBLE
            floatingAddLocation.visibility = View.VISIBLE
            locationsAdapter.refresh(list.toMutableList())
        } else {
            addLocation.visibility = View.VISIBLE
            locationHint.visibility = View.VISIBLE
            floatingAddLocation.visibility = View.GONE
            rvLocations.visibility = View.GONE
        }
    }
}