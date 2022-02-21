package com.example.android.rest_testing.net.retrofit

import android.util.Log
import android.widget.Toast
import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.RestInterface
import retrofit.ResponseCallback
import retrofit.RestAdapter

class RestRetrofit: RestInterface {
    private val userService: UserService
    private val placeService: PlaceService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP + ":8080/"
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseURL)
            .build()

        userService = retrofit.create(UserService::class.java)
        placeService = retrofit.create(PlaceService::class.java)
    }

    override fun loginUser(user: UserShort): JWT? {
        try {
            val jwt = userService.loginUser(user)
            return jwt
        }
        catch (ex: Exception) {
            Log.d("custom", ""+ex.toString())
        }
        return null
    }

    override fun registerUser(user: User): Boolean {
        try{
            var registeredUser = userService.registerUser(user)
            println(registeredUser)
            return true
        }
        catch (ex: Exception) {
            Log.d("custom", ""+ex.toString());
        }
        return false
    }

    override fun savePlace(userPlace: UserPlace): Boolean {
        try {
            var savedLocation = placeService.savePlace(userPlace.getToken(), userPlace.getPlace())
            println(savedLocation)
            return true
        }
        catch (ex: Exception){
            Log.d("custom", ""+ex.toString())
        }
        return false
    }

    override fun getSavedPlaces(userIndex: UserIndex): MutableList<PlaceResponse> {
        return placeService.getPlaces(userIndex.getToken(), userIndex.fromIndex.toString(), userIndex.toIndex.toString())
    }

    override fun deletePlace(userPlace: UserPlace): Boolean {
        try {
            placeService.deletePlace(userPlace)
            return true
        }
        catch (ex: Exception){
            Log.d("custom", ""+ex.toString())
        }
        return false
    }

}