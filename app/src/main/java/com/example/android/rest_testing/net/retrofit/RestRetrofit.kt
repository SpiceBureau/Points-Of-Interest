package com.example.android.rest_testing.net.retrofit

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.example.android.rest_testing.LoginScene
import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.RestInterface
import retrofit.ResponseCallback
import retrofit.RestAdapter
import retrofit.RetrofitError

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

    override fun savePlace(userPlace: UserPlace, context: Context): SimpleResponse {
        var statusCode:Int
        try {
            var savedLocation = placeService.savePlace(userPlace.getToken(), userPlace.getPlace())
            return SimpleResponse(true, 201)
        }
        catch (ex: RetrofitError){
            Log.d("custom", ""+ex.toString())
            println(ex.message)
            statusCode = ex.response.status
        }
        return SimpleResponse(false, statusCode)
    }

    override fun deletePlace(userPlace: UserPlace): Boolean {
        try {
            placeService.deletePlace(userPlace.getToken(), userPlace.getPlaceName())
            return true
        }
        catch (ex: Exception){
            Log.d("custom", ""+ex.toString())
        }
        return false
    }

    override fun getSavedPlaces(token: JWT?, fromIndex:Int, toIndex:Int, keyWord: String?, type: String?): MutableList<PlaceResponse> {
        return placeService.getPlacesFiltered(token.toString(), fromIndex, toIndex, keyWord, type)
    }

}