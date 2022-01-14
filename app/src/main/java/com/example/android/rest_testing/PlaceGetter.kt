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
        if ((type == "null") or (location == "null")){
            Toast.makeText(context, "Choose a type and distance", Toast.LENGTH_SHORT).show()
            return
        }
        val url =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=45.8283431199498, 16.08856418358347&radius=$distance&type=$type&key=AIzaSyCzERXhFAlFCdThBTQfboMUx6ajssHSxnA"

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
            volleyResponseListener.onResponse(it)
        }, {
            volleyResponseListener.onError("Didn't work")
        })
        MySingleton.getInstance(context).addToRequestQueue(request)
    }
}
