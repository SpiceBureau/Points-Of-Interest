package com.example.android.rest_testing

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.rest_testing.entity.Place
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.net.RestFactory
import kotlinx.coroutines.*

class PlacesViewHolder(v:View, adapter: PlacesAdapter): RecyclerView.ViewHolder(v) {
    val placeAdapter = adapter
    var placeNameTextView: TextView? = null
    var placeTypeTextView: TextView? = null
    var deleteNoteButton: ImageButton? = null
    var MapButton: ImageButton? = null

    var latitude: Double? = null
    var longitude: Double? = null

    init {
        placeNameTextView = v.findViewById(R.id.tvItemTitle)
        placeTypeTextView = v.findViewById(R.id.tvLocationType)
        deleteNoteButton = v.findViewById(R.id.btnDelete)
        MapButton = v.findViewById(R.id.btnMap)

        deleteNoteButton?.setOnClickListener {
            var position = adapterPosition
            CoroutineScope(Dispatchers.IO).launch {
                val rest = RestFactory.instance
                val place = Place(name = placeNameTextView?.text.toString())
                if(placeAdapter.token != null) {
                    val response = rest.deletePlace(UserPlace(placeAdapter.token!!, place))
                    withContext(Dispatchers.Main){
                        if(response.passed){
                            PlacesRepository.listOfPlaces.removeAt(position)
                            placeAdapter.notifyItemRemoved(position)
                        }
                        else{
                            if(response.status == 401){
                                val toast = Toast.makeText(v.context,"Authentification failed. Login again.",
                                    Toast.LENGTH_SHORT)
                                toast.show()
                                val extras = Bundle()
                                extras.putString("username", "null")
                                val intent = Intent(v.context, LoginScene::class.java)
                                intent.putExtras(extras)
                                startActivity(v.context, intent, Bundle())
                            }
                            else{
                                val toast = Toast.makeText(v.context,"Error occured. Try again.", Toast.LENGTH_SHORT)
                                toast.show()
                            }
                        }
                    }
                }
            }
        }

        MapButton?.setOnClickListener {
            val mapsActivity = MapsActivity2()
            val intent = Intent(v.context, mapsActivity::class.java)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            startActivity(v.context, intent, null)
        }
    }
}