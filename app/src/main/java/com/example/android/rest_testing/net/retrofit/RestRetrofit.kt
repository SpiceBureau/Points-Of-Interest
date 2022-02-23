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

    override fun loginUser(user: UserShort): LoginResponse {
        val statusCode:Int
        try {
            val jwt = userService.loginUser(user)
            return LoginResponse(jwt, true, 200)
        }
        catch (ex: RetrofitError) {
            Log.d("custom", ""+ex.toString())
            statusCode = ex.response.status
        }
        return LoginResponse(null, false, statusCode)
    }

    override fun registerUser(user: User): SimpleResponse {
        val statusCode:Int
        try{
            var registeredUser = userService.registerUser(user)
            println(registeredUser)
            return SimpleResponse(true, 201)
        }
        catch (ex: RetrofitError) {
            Log.d("custom", ""+ex.toString());
            statusCode = ex.response.status
        }
        return SimpleResponse(false, statusCode)
    }

    override fun savePlace(userPlace: UserPlace, context: Context): SimpleResponse {
        val statusCode:Int
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

    override fun deletePlace(userPlace: UserPlace): SimpleResponse {
        val statusCode:Int
        try {
            placeService.deletePlace(userPlace.getToken(), userPlace.getPlaceName())
            return SimpleResponse(true, 200)
        }
        catch (ex: RetrofitError){
            Log.d("custom", ""+ex.toString())
            statusCode = ex.response.status
        }
        return SimpleResponse(false, statusCode)
    }

    override fun getSavedPlaces(token: JWT?, fromIndex:Int, toIndex:Int, keyWord: String?, type: String?): MutableList<PlaceResponse> {
        return placeService.getPlacesFiltered(token.toString(), fromIndex, toIndex, keyWord, type)
    }

}