package com.example.android.rest_testing

import android.app.Dialog
import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import org.json.JSONObject

import android.view.Window
import com.google.android.gms.maps.model.LatLng


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

                        val placeInfo = PlaceInfo(response, applicationContext)

                        val arrayAdapter = ArrayAdapter(applicationContext, R.layout.listviewlayout, placeInfo.getListOfNames())
                        listView.adapter = arrayAdapter
                        listView.setOnItemClickListener { parent, view, position, id ->
                            val element = arrayAdapter.getItem(position)
                            val loc = placeInfo.getLocation(id.toInt())
                            showDialog(element.toString(), loc)
                        }
                    }
                    override fun onError(message: String) {
                        Toast.makeText(applicationContext, "Didn't work", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
    private fun showDialog(title: String, loc: LatLng) {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popup)
        val body = dialog.findViewById(R.id.tvPopup) as TextView
        body.text = title
        val btnSave = dialog.findViewById(R.id.btnSave) as Button
        val btnMap = dialog.findViewById(R.id.btnMap) as Button
        val btnX = dialog.findViewById(R.id.btnExit) as Button
        btnX.setOnClickListener {
            dialog.dismiss()
        }
        btnMap.setOnClickListener {
            val mapsActivity = MapsActivity()
            val intent = Intent(this, mapsActivity::class.java)
            intent.putExtra("latitute", loc.latitude)
            intent.putExtra("longitude", loc.longitude)
            startActivity(intent)
        }
        dialog.show()
    }
}