package com.example.android.rest_testing

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject


class ItemAdapter(iL: List<String>, locL: List<Any>, val itemClick: (LatLng) -> Unit): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val itemList = iL
    private val listOfCoordinates = locL

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val itemTitle: TextView = itemView.findViewById(R.id.tvItemTitle)
        val btnMap = itemView.findViewById<ImageButton>(R.id.btnMap)
        val btnSave = itemView.findViewById<ImageButton>(R.id.btnSave)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemTitle.text = itemList[position]

        val latLng = listOfCoordinates[position] as JSONObject

        holder.btnMap.setOnClickListener {
            itemClick(LatLng(latLng.get("lat") as Double, latLng.get("lng") as Double))
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}