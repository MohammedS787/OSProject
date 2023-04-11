package com.example.osproject

import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.osproject.databinding.ActivityMapsBinding
import com.example.osproject.extension.animateFadeVisibility
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding


    private val permissionRequest = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListeners()

        permissionRequest.launch(
            arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        setupMapStyle(mMap)

        // Add a marker in Mansoura and move the camera
        val mansoura = LatLng(31.037933, 31.381523)
        mMap.addMarker(MarkerOptions().position(mansoura).title("Marker in Mansoura"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mansoura))

        mMap.setOnMapClickListener {
            mMap.clear()
            addMarkerAndShowCoordinates(it)
//            locationClient
//                .getLocationUpdates(10000L)
//                .catch {e -> e.printStackTrace()}
//                .onEach { location ->
//                    val lat = location.latitude
//                    val long = location.longitude
//                    distance = calculateDistanceInMeters(lat, long, it.latitude, it.longitude)
//                }
        }
    }

    private fun setListeners() {
        binding.cardViewAlarm.setOnClickListener {
            startService()
            it.animateFadeVisibility(false)
        }
    }

    private fun addMarkerAndShowCoordinates(latLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))

        binding.cardViewAlarm.animateFadeVisibility(true)
    }

    private fun calculateDistanceInMeters(latOne: Double, longOne: Double,
                                          latTwo: Double, longTwo: Double) : Float {
        val loc1 = Location("").apply {
            latitude = latOne
            longitude = longOne
        }
        val loc2 = Location("").apply {
            latitude = latTwo
            longitude= longTwo
        }

        return loc1.distanceTo(loc2)
    }

    private fun startService() {
        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }
    }

    @Suppress("MissingPermission")
    private fun setupMapStyle(map: GoogleMap) {
        if (hasLocationPermission()) {
            map.isMyLocationEnabled = true
            map.uiSettings.isMyLocationButtonEnabled = true
        }

        map.uiSettings.apply {
            isZoomGesturesEnabled = true
            isScrollGesturesEnabled = true
            isRotateGesturesEnabled = true
            isTiltGesturesEnabled = true
            isZoomControlsEnabled = true
            isCompassEnabled = true
            isMapToolbarEnabled = true
        }
    }
}