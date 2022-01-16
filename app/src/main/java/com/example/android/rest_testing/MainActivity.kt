package com.example.android.rest_testing

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import android.view.Window
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.android.volley.toolbox.JsonObjectRequest
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response


class MainActivity : AppCompatActivity() {

    lateinit var actionBarToggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_layout)

        val btnSearch = findViewById<Button>(R.id.btnSearch)
        val etLocation = findViewById<EditText>(R.id.etLocation)
        val etRadius = findViewById<EditText>(R.id.etRadius)
        val etType = findViewById<EditText>(R.id.etType)
        val listView = findViewById<ListView>(R.id.lvJSONList)

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout)
        val navigationView: NavigationView = findViewById(R.id.navigationView)


        actionBarToggle = object : ActionBarDrawerToggle(
            this,
            drawerLayout,
            R.string.menu_open,
            R.string.menu_close
        ){
            override fun onDrawerClosed(view: View){
                super.onDrawerClosed(view)
            }

            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
            }
        }

        actionBarToggle.isDrawerIndicatorEnabled = true
        drawerLayout.addDrawerListener(actionBarToggle)
        actionBarToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        navigationView.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.pointOfInterestSearch -> Toast.makeText(applicationContext, "Points of interest search", Toast.LENGTH_SHORT).show()
                R.id.myPointOfInterest -> Toast.makeText(applicationContext, "My points of interest", Toast.LENGTH_SHORT).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        return if (actionBarToggle.onOptionsItemSelected(item)) {
            true
        } else super.onOptionsItemSelected(item!!)
        // Handle your other action bar items...
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

        //lokacija se sprema ako je pokrenut json-server i postoji json file sa praznim poljem-> {favorites:[]}
        //zasad se spremaju samo naziv i lokacije, tip nisam uspio izvući, dobivam prazan string
        btnSave.setOnClickListener {
            val location = JSONObject()
            location.put("latitude", loc.latitude)
            location.put("longitude", loc.longitude)
            val place = JSONObject()
            place.put("name", title)
            place.put("location", location)

            val url = "http://10.0.2.2:3000/favorites" //10.0.2.2 je adresa računala kad se pokrene emulator

            val request = JsonObjectRequest(Request.Method.POST, url, place,
                { response ->
                    System.out.println(response)
                },
                { error -> System.out.println(error) })
            MySingleton.getInstance(this).addToRequestQueue(request)
        }
        dialog.show()
    }
}