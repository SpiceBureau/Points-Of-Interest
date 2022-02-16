package com.example.android.rest_testing

import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlacesViewHolder(v:View, adapter: PlacesAdapter): RecyclerView.ViewHolder(v) {
    val placeAdapter = adapter
    var placeNameTextView: TextView? = null
    var placeTypeTextView: TextView? = null
    var deleteNoteButton: ImageButton? = null

    init {
        placeNameTextView = v.findViewById(R.id.placeNameTextView)
        placeTypeTextView = v.findViewById(R.id.placeTypeTextView)
        deleteNoteButton = v.findViewById(R.id.deletePlaceButton)
    }
}