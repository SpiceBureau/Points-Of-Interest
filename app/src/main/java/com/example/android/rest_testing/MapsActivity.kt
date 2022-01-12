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




class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }
    override fun onMapReady(googleMap: GoogleMap) {
        val intent1 = intent
        val lat = intent1.getDoubleExtra("latitute", 0.0)
        val lng = intent1.getDoubleExtra("longitude", 0.0)

        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val markerForMap = LatLng(lat, lng)
        val zoomLevel = 14.5f //This goes up to 21

        mMap.addMarker(MarkerOptions().position(markerForMap).title("Marker in Sydney"))
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerForMap, zoomLevel))
    }
}