package com.example.android.rest_testing

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.android.rest_testing.entity.Place
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.retrofit.JWT
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject


class ItemAdapter(type:String, jwtToken: JWT, iL: List<String>, locL: List<Any>, userLoc: String, cnt: Context, val itemClick: (LatLng) -> Unit): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val locationType = type
    private val token = jwtToken
    private val itemList = iL
    private val listOfCoordinates = locL
    private val context = cnt
    private val locationList = locL
    private val userLocationString = userLoc

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val btnMap = itemView.findViewById<ImageButton>(R.id.btnMap)
        val btnSave = itemView.findViewById<ImageButton>(R.id.btnSave)
        val tvDistance = itemView.findViewById<TextView>(R.id.tvDistance)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = itemList[position]

        val itemLocationJSON = locationList[position] as JSONObject
        val userLocationAsList: List<String> = userLocationString.split(",").map { it -> it.trim() }

        val itemLocation = Location("Item location")
        itemLocation.latitude = itemLocationJSON.get("lat").toString().toDouble()
        itemLocation.longitude = itemLocationJSON.get("lng").toString().toDouble()

        val userLocation = Location("User location")
        userLocation.latitude = userLocationAsList[0].toDouble()
        userLocation.longitude = userLocationAsList[1].toDouble()

        holder.tvDistance.text = "Distance = " + itemLocation.distanceTo(userLocation).toInt().toString() +" m"

        val cardView: CardView = holder.itemView.findViewById(R.id.cardView)
        if (position % 2 == 0)
            cardView.setCardBackgroundColor(Color.parseColor("#B3FFFFFF"))
        else
            cardView.setCardBackgroundColor(Color.parseColor("#f0f0f0"))

        val latLng = listOfCoordinates[position] as JSONObject

        holder.btnMap.setOnClickListener {
            itemClick(LatLng(latLng.get("lat") as Double, latLng.get("lng") as Double))
        }
        holder.btnSave.setOnClickListener {
            CoroutineScope(Dispatchers.IO).launch {
                val rest = RestFactory.instance
                val place = Place(holder.itemTitle.text as String, locationType, itemLocation.latitude, itemLocation.longitude)
                val userPlace = UserPlace(token, place)
                val result = rest.savePlace(userPlace, context)
                withContext(Dispatchers.Main) {
                    if (result.passed) {
                        PlacesRepository.listOfPlaces.clear()
                        val toast = Toast.makeText(context, "Location saved.", Toast.LENGTH_SHORT)
                        toast.show()
                    }
                    else{
                        if(result.status == 401) {
                            val toast = Toast.makeText(context,"Authentification failed. Login again.",Toast.LENGTH_SHORT)
                            toast.show()
                            val extras = Bundle()
                            extras.putString("username", "null")
                            val intent = Intent(context, LoginScene::class.java)
                            intent.putExtras(extras)
                            startActivity(context, intent, Bundle())
                        }
                        else if(result.status == 406){
                            val toast = Toast.makeText(context,"Location already saved.",Toast.LENGTH_SHORT)
                            toast.show()
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}