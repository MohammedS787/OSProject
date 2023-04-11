package com.example.osproject

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.osproject.databinding.ActivityMapsBinding
import com.google.android.gms.maps.model.Marker
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import kotlin.properties.Delegates

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var locationClient: LocationClient
    private var distance by Delegates.notNull<Float>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            0
        )

        Intent(applicationContext, LocationService::class.java).apply {
            action = LocationService.ACTION_START
            startService(this)
        }

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

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

    private fun addMarkerAndShowCoordinates(latLng: LatLng) {
        mMap.addMarker(MarkerOptions().position(latLng).title("Selected Location"))
        val latitude = latLng.latitude
        val longitude = latLng.longitude
        Toast.makeText(this,
            "Latitude: $latitude, Longitude: $longitude",
        Toast.LENGTH_SHORT).show()
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
}