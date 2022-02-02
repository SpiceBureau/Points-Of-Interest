package com.example.android.rest_testing

import android.content.Context
import android.content.Intent
import android.graphics.Color
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
import com.google.android.gms.maps.model.LatLng
import org.json.JSONObject


class ItemAdapter(iL: List<String>, locL: List<Any>, cnt: Context, val itemClick: (LatLng) -> Unit): RecyclerView.Adapter<ItemAdapter.ViewHolder>() {

    private val itemList = iL
    private val listOfCoordinates = locL
    private val context = cnt

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

        if (position % 2 == 0)
            holder.itemView.setBackgroundColor(Color.parseColor("#B3FFFFFF"))
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#f0f0f0"))

        val latLng = listOfCoordinates[position] as JSONObject

        holder.btnMap.setOnClickListener {
            itemClick(LatLng(latLng.get("lat") as Double, latLng.get("lng") as Double))
        }
        holder.btnSave.setOnClickListener {
            val location = JSONObject()
            location.put("latitude", latLng.get("lat"))
            location.put("longitude", latLng.get("lng"))
            val place = JSONObject()
            place.put("name", itemList[position])
            place.put("location", location)

            val url =
                "http://localhost:3000/favorites" //10.0.2.2 je adresa računala kad se pokrene emulator

            val request = JsonObjectRequest(
                Request.Method.POST, url, place,
                { response ->
                    println(response)
                },
                { error ->
                    println(error) })
            MySingleton.getInstance(context).addToRequestQueue(request)
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }
}