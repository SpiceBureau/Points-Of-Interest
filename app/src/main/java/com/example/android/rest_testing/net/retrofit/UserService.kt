package com.example.android.rest_testing.net.retrofit

import com.example.android.rest_testing.entity.UserShort
import retrofit.Callback
import retrofit.ResponseCallback
import retrofit.http.Body
import retrofit.http.POST

interface UserService {
    @POST("/loginUser")
    fun loginUser(@Body user:UserShort): UserResponse
}