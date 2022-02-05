package com.example.android.rest_testing.net.retrofit

import android.util.Log
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import com.example.android.rest_testing.net.RestInterface
import retrofit.ResponseCallback
import retrofit.RestAdapter

class RestRetrofit: RestInterface {
    private val userService: UserService

    init {
        val baseURL = "http://" + RestFactory.BASE_IP + ":8080/"
        val retrofit = RestAdapter.Builder()
            .setEndpoint(baseURL)
            .build()

        userService = retrofit.create(UserService::class.java)
    }

    override fun loginUser(user: UserShort): Boolean {
        try {
            var loggedUser = userService.loginUser(user)
            println(loggedUser)
            return true
        }
        catch (ex: Exception) {
            Log.d("custom", ""+ex.toString());
        }
        return false
    }
}