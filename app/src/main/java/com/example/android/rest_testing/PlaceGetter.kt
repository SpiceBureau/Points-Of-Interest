package com.example.android.rest_testing

import android.content.Context
import android.service.autofill.Validators.or
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class PlaceGetter constructor(private val context: Context){

    interface VolleyResponseListener{
        fun onError(message: String){}
        fun onResponse(response: JSONObject){}
    }

    fun getStuff(type: String, distance: String, location: String, volleyResponseListener: VolleyResponseListener){
        if (location == ""){
            Toast.makeText(context, "You have to allow the app to use your location to find nearby points of interest", Toast.LENGTH_LONG).show()
            return
        }
        if (distance == "0"){
            Toast.makeText(context, "Choose a distance", Toast.LENGTH_SHORT).show()
            return
        }
        val url =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$location&radius=$distance&type=$type&key=AIzaSyCzERXhFAlFCdThBTQfboMUx6ajssHSxnA"

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
            volleyResponseListener.onResponse(it)
        }, {
            volleyResponseListener.onError("Didn't work")
        })
        MySingleton.getInstance(context).addToRequestQueue(request)
    }
}
