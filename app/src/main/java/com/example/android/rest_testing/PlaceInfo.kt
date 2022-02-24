package com.example.android.rest_testing

import android.content.Context
import android.widget.Toast
import com.google.android.gms.maps.model.LatLng
import org.json.JSONException
import org.json.JSONObject

class PlaceInfo(response: JSONObject, val context: Context){
    val listOfNames: MutableList<String> = mutableListOf<String>()
    val listOfRatings: MutableList<String> = mutableListOf<String>()
    val listOfCoordinates: MutableList<Any> = mutableListOf()
    val results = response.getJSONArray("results")
    var nextPageToken: String = "null"

    init {
        nextPageToken = try {
            response.getString("next_page_token")
        } catch (e: JSONException){
            "null"
        }

        for (i in 0 until results.length()){
            val jsonObject: JSONObject = results.getJSONObject(i)
            listOfNames.add(jsonObject.get("name").toString())

            val geometry: JSONObject = jsonObject.get("geometry") as JSONObject
            val loc = geometry.get("location")

            var rating = "No rating"
            if(jsonObject.has("rating")){
                rating = jsonObject.get("rating").toString()
            }
            listOfRatings.add(rating)

            listOfCoordinates.add(loc)
        }
    }

    @JvmName("getListOfNames1")
    fun getListOfNames(): MutableList<String>{
        return listOfNames
    }

    fun getName(id: Int): String{
        return listOfNames[id]
    }

    fun getLocation(id: Int): LatLng {
        val geometry: JSONObject = listOfCoordinates[id] as JSONObject

        val latitude: Double = geometry.get("lat") as Double
        val longitude: Double = geometry.get("lng") as Double
        return LatLng(latitude, longitude)
    }
}