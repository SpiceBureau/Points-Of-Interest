package com.example.android.rest_testing

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lateinit var lastLocation: Location
        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnLocation = findViewById<Button>(R.id.btnLocation)
        val etRadius = findViewById<EditText>(R.id.etRadius)
        val etType = findViewById<EditText>(R.id.etType)
        val listView = findViewById<ListView>(R.id.lvJSONList)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnLocation.setOnClickListener {
            if (ActivityCompat.checkSelfPermission(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
                Toast.makeText(applicationContext, "Didn't get location", Toast.LENGTH_SHORT).show()
            }
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location : Location? ->
                    val lat = location?.latitude.toString()
                    val longt = location?.longitude.toString()
                    Toast.makeText(applicationContext, "$lat, $longt", Toast.LENGTH_SHORT).show()
                }
        }

        btnSearch.setOnClickListener {
            val type = etType.text
            val radius = etRadius.text
            val placegetter = PlaceGetter(applicationContext)
            placegetter.getStuff(
                type.toString(),
                "45.83129519276554, 16.089334157070603",
                radius.toString(),
                object : PlaceGetter.VolleyResponseListener {
                    override fun onResponse(response: JSONObject) {
                        val listForShowing: MutableList<String> = mutableListOf<String>()
                        val results = response.getJSONArray("results")

                        for (i in 0 until results.length()){
                            val jsonObject: JSONObject = results.getJSONObject(i)
                            listForShowing.add(jsonObject.get("name").toString())
                        }

                        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.listviewlayout, listForShowing)
                        listView.adapter = arrayAdapter
                        listView.setOnItemClickListener { parent, view, position, id ->
                            val element = arrayAdapter.getItem(position)
                            Toast.makeText(applicationContext, element.toString(), Toast.LENGTH_SHORT).show()
                        }
                    }
                    override fun onError(message: String) {
                        Toast.makeText(applicationContext, "Didn't work", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
    private fun setUpMap()  {
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                val lastLocation = location
            }
    }
}
