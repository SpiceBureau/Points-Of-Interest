package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.retrofit.JWT
import com.example.android.rest_testing.net.retrofit.PlaceResponse
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_saved_poiactivity.*
import kotlinx.coroutines.*

class SavedPOIActivity : AppCompatActivity(), SwipeRefreshLayout.OnRefreshListener {

    lateinit var actionBarToggle: ActionBarDrawerToggle
    val placesAdapter = PlacesAdapter()
    var isLastPage: Boolean = false
    var isLoading: Boolean = false
    var placesSearch = PlacesSearch(null, null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_saved_poiactivity)

        val token: JWT = intent.getSerializableExtra("token") as JWT
        placesAdapter.token = token

        savedPlacesRecyclerView.layoutManager = LinearLayoutManager(this)
        savedPlacesRecyclerView.adapter = placesAdapter

        savedPlacesRecyclerView.addOnScrollListener(object: PaginationScrollListener(savedPlacesRecyclerView.layoutManager as LinearLayoutManager){
            override fun loadMoreItems() {
                isLoading = true
                getMoreItems(token)
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        val drawerLayout: DrawerLayout = findViewById(R.id.drawerLayout2)
        val navigationView: NavigationView = findViewById(R.id.navigationView2)

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
                R.id.pointOfInterestSearch -> {
                    val loadingActivity = LoadingActivity()
                    val intent = Intent(this, loadingActivity::class.java)
                    val bundle = Bundle()
                    bundle.putString("key1", "SavedPOIActivity")
                    intent.putExtras(bundle)
                    intent.putExtra("token", token)
                    startActivity(intent)
                    finish()
                }
                R.id.myPointOfInterest -> Toast.makeText(
                    applicationContext,
                    "Already on this screen",
                    Toast.LENGTH_SHORT
                ).show()
            }
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

    showFilterButton.setOnClickListener {
        val filterDialog = FilterDialog(this, placesAdapter.token, placesSearch)
        filterDialog.showDialog()
    }

    searchButton.setOnClickListener {
        isLastPage = false
        PlacesRepository.listOfPlaces.clear()
        placesAdapter.notifyDataSetChanged()
        getMoreItems(token)
    }

    if(PlacesRepository.listOfPlaces.size == 0) {
        getMoreItems(token)
    }

    }

    private fun getMoreItems(token: JWT) {
        var savedPlaces = CoroutineScope(Dispatchers.IO).async {
            val rest = RestFactory.instance
            var fromIndex = PlacesRepository.listOfPlaces.size
            var toIndex = fromIndex + 10
            rest.getSavedPlaces(token, fromIndex, toIndex, placesSearch.keyWord, placesSearch.typeOfPlace)
        }

        CoroutineScope(Dispatchers.IO).launch {
            var places = savedPlaces.await()
            withContext(Dispatchers.Main){
                if(places.size == 0){
                    isLastPage = true
                }
                else {
                    PlacesRepository.listOfPlaces.addAll(places)
                    placesAdapter.notifyDataSetChanged()
                }
            }
            isLoading = false
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

    override fun onRefresh() {
        println("Refreshing")
    }
}