package com.example.android.rest_testing

import android.content.Intent
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.android.rest_testing.entity.Place
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.net.RestFactory
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

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
            PlacesRepository.listOfPlaces.removeAt(position)
            placeAdapter.notifyItemRemoved(position)
            CoroutineScope(Dispatchers.IO).launch {
                val rest = RestFactory.instance
                val place = Place(name = placeNameTextView?.text.toString())
                if(placeAdapter.token != null) {
                    rest.deletePlace(UserPlace(placeAdapter.token!!, place))
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