package com.example.android.rest_testing

import android.content.Context
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import org.json.JSONObject

class PlaceGetter constructor(private val context: Context){

    interface VolleyResponseListener{
        fun onError(message: String){}
        fun onResponse(response: JSONObject){}
    }

    fun getStuff(type: String, location: String, radius: String, volleyResponseListener: VolleyResponseListener){
        val url =
            "https://maps.googleapis.com/maps/api/place/nearbysearch/json?location=$location&radius=$radius&type=$type&key=AIzaSyCzERXhFAlFCdThBTQfboMUx6ajssHSxnA"

        val request = JsonObjectRequest(Request.Method.GET, url, null, {
            volleyResponseListener.onResponse(it)
        }, {
            volleyResponseListener.onError("Didn't work")
        })
        MySingleton.getInstance(context).addToRequestQueue(request)
    }
}
