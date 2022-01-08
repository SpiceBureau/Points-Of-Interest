package com.example.android.rest_testing

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.gson.JsonObject
import org.json.JSONObject


class MainActivity : AppCompatActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var lastLocation: Location

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etRadius = findViewById<EditText>(R.id.etRadius)
        val etType = findViewById<EditText>(R.id.etType)
        val listView = findViewById<ListView>(R.id.lvJSONList)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        btnSearch.setOnClickListener {
            val type = etType.text
            val location = etLocation.text
            val radius = etRadius.text
            val placegetter = PlaceGetter(applicationContext)
            placegetter.getStuff(
                type.toString(),
                location.toString(),
                radius.toString(),
                object : PlaceGetter.VolleyResponseListener {
                    override fun onResponse(response: JSONObject) {
                        var listForShowing: MutableList<String> = mutableListOf<String>()
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
}