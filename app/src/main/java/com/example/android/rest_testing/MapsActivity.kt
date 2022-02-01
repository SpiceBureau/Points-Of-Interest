package com.example.android.rest_testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.example.android.rest_testing.databinding.ActivityMapsBinding
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    companion object {
        const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        val intent1 = intent
        val lat = intent1.getDoubleExtra("latitude", 0.0)
        val lng = intent1.getDoubleExtra("longitude", 0.0)

        mMap = googleMap

        val markerForMap = LatLng(lat, lng)
        val zoomLevel = 14.5f //This goes up to 21

        mMap.addMarker(MarkerOptions().position(markerForMap))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerForMap, zoomLevel))

        /*setUpMap()*/
    }

    private fun setUpMap() {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        mMap.isMyLocationEnabled = true
    }

}