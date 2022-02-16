package com.example.android.rest_testing

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
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

    init {
        placeNameTextView = v.findViewById(R.id.placeNameTextView)
        placeTypeTextView = v.findViewById(R.id.placeTypeTextView)
        deleteNoteButton = v.findViewById(R.id.deletePlaceButton)

        deleteNoteButton?.setOnClickListener {
            var position = adapterPosition
            PlacesRepository.listOfPlaces.removeAt(position)
            placeAdapter.notifyItemRemoved(position)
            CoroutineScope(Dispatchers.IO).launch {
                val rest = RestFactory.instance
                val place = Place(name = placeNameTextView?.text.toString())
                if(placeAdapter.user != null) {
                    rest.deletePlace(UserPlace(placeAdapter.user!!, place))
                }
            }
        }
    }
}