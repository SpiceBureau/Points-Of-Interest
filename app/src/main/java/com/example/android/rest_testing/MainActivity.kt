package com.example.android.rest_testing

import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import com.android.volley.Request
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices


class MainActivity : AppCompatActivity() {

    lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_actiity_layout)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val btnGetLoc = findViewById<Button>(R.id.btnGetLoc)
        val slider = findViewById<SeekBar>(R.id.seekBar)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val tvForSeeker = findViewById<TextView>(R.id.tvForSeeker)


        var typeForSearch: String = "null"
        var distance: String = "0"

        val spinnerArray = listOf<String>(
            "airport",
            "atm",
            "bakery",
            "bank",
            "book_store",
            "bus_station",
            "cafe",
            "car_wash",
            "casino",
            "church",
            "clothing_store",
            "dentist",
            "doctor",
            "gas_station",
            "gym",
            "hospital",
            "library",
            "movie_theater",
            "museum",
            "night_club",
            "park",
            "parking",
            "pet_store",
            "pharmacy",
            "police",
            "post_office",
            "restaurant",
            "school",
            "shopping_mall",
            "stadium",
            "store",
            "supermarket",
            "train_station",
            "university",
            "zoo"
        )

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(applicationContext)

        ArrayAdapter.createFromResource(  //spinner stuff
            this,
            R.array.planets_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        actionBarToggle = object : ActionBarDrawerToggle( //drawer stuff
            this,
            drawerLayout,
            R.string.menu_open,
            R.string.menu_close
        ) {
            override fun onDrawerClosed(view: View) {
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View) {
                super.onDrawerOpened(drawerView)
            }
        }

        actionBarToggle.isDrawerIndicatorEnabled = true // more drawer stuff
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navigationView.setNavigationItemSelectedListener { // navigation for drawer menu
            when (it.itemId) {
                R.id.pointOfInterestSearch -> Toast.makeText(
                    applicationContext,
                    "Already on this screen",
                    Toast.LENGTH_SHORT
                ).show()
                R.id.myPointOfInterest -> {
                    val savedPOIActivity = SavedPOIActivity()
                    val intent = Intent(this, savedPOIActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        spinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener { // spinner stuff
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    typeForSearch = spinnerArray[position]
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {}
            }

        slider.setOnSeekBarChangeListener(object : // Distance slider
            SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seek: SeekBar, progress: Int, fromUser: Boolean) {
                tvForSeeker.text = "Distance = " + seek.progress + " m"
            }

            override fun onStartTrackingTouch(seek: SeekBar) {
                Toast.makeText(applicationContext, "Tip: You can manually enter the distance by clicking the 'Distance' text", Toast.LENGTH_LONG).show()
            }

            override fun onStopTrackingTouch(seek: SeekBar) {
                distance = seek.progress.toString()
            }
        })

        btnGetLoc.setOnClickListener{
            getLastKnownLocation()
        } // Get location btn

        btnSearch.setOnClickListener { //search button
            val tvLocation = findViewById<TextView>(R.id.tvLocation)
            val placegetter = PlaceGetter(applicationContext)
            placegetter.getStuff(
                typeForSearch,
                distance,
                tvLocation.text as String,
                object : PlaceGetter.VolleyResponseListener {
                    override fun onResponse(response: JSONObject) {
                        val placeInfo = PlaceInfo(response, applicationContext)
                        val adapter = ItemAdapter(placeInfo.getListOfNames(), placeInfo.listOfCoordinates){ latlng ->
                            val mapsActivity = MapsActivity2()
                            val intent = Intent(this@MainActivity, mapsActivity::class.java)
                            intent.putExtra("latitude", latlng.latitude)
                            intent.putExtra("longitude", latlng.longitude)
                            startActivity(intent)
                        }

                        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                        recyclerView.adapter = adapter
                        /*val arrayAdapter = ArrayAdapter(
                            applicationContext,
                            R.layout.listviewlayout,
                            placeInfo.getListOfNames()
                        )
                        listView.adapter = arrayAdapter
                        listView.setOnItemClickListener { parent, view, position, id ->
                            val element = arrayAdapter.getItem(position)
                            val loc = placeInfo.getLocation(id.toInt())
                            showDialog(element.toString(), loc)
                        }*/
                    }
                    override fun onError(message: String) {
                        Toast.makeText(applicationContext, "Didn't work", Toast.LENGTH_SHORT).show()
                    }
                })
        }
        tvForSeeker.setOnClickListener{
            val dialog_distance = Dialog(this)
            dialog_distance.requestWindowFeature(Window.FEATURE_NO_TITLE)

            dialog_distance.setCancelable(true)
            dialog_distance.setContentView(R.layout.popup_for_distance_input)

            val distanceInput = dialog_distance.findViewById<EditText>(R.id.etPopupDistance)
            distanceInput.setOnEditorActionListener { v, actionId, event ->
                if(actionId == EditorInfo.IME_ACTION_DONE){
                    tvForSeeker.text = "Distance = " + distanceInput.text + " m"
                    dialog_distance.dismiss()
                    true
                } else {
                    false
                }
            }

            dialog_distance.show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean { // burger and back button for drawer
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        return if (actionBarToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item)
        // Handle your other action bar items...
    }

    private fun showDialog(title: String, loc: LatLng) { // popupForListView window stuff

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        dialog.setCancelable(false)
        dialog.setContentView(R.layout.popup_for_lv)
        val body = dialog.findViewById(R.id.tvPopup) as TextView
        body.text = title
        val btnSave = dialog.findViewById(R.id.btnSave) as Button
        val btnMap = dialog.findViewById(R.id.btnMap) as ImageButton
        val btnX = dialog.findViewById(R.id.btnExt) as ImageButton
        btnX.setOnClickListener {
            dialog.dismiss()
        }
        btnMap.setOnClickListener {
            val mapsActivity = MapsActivity2()
            val intent = Intent(this, mapsActivity::class.java)
            intent.putExtra("latitude", loc.latitude)
            intent.putExtra("longitude", loc.longitude)
            startActivity(intent)
        }

        //lokacija se sprema ako je pokrenut json-server i postoji json file sa praznim poljem-> {favorites:[]}
        //zasad se spremaju samo naziv i lokacije, tip nisam uspio izvući, dobivam prazan string
        btnSave.setOnClickListener {
            val location = JSONObject()
            location.put("latitude", loc.latitude)
            location.put("longitude", loc.longitude)
            val place = JSONObject()
            place.put("name", title)
            place.put("location", location)

            val url =
                "http://10.0.2.2:3000/favorites" //10.0.2.2 je adresa računala kad se pokrene emulator

            val request = JsonObjectRequest(Request.Method.POST, url, place,
                { response ->
                    println(response)
                },
                { error -> println(error) })
            MySingleton.getInstance(this).addToRequestQueue(request)
        }
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
    }

    private fun getLastKnownLocation() {
        var lastLoc: LatLng
        val tvLocation = findViewById<TextView>(R.id.tvLocation)
        if (ActivityCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                MapsActivity2.LOCATION_PERMISSION_REQUEST_CODE
            )
            tvLocation.text = "Click the button again"
            return
        }
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location->
                if (location != null) {
                    lastLoc = LatLng(location.latitude, location.longitude)
                    tvLocation.text = lastLoc.latitude.toString() + ", " + lastLoc.longitude.toString()
                }

            }
    }
}