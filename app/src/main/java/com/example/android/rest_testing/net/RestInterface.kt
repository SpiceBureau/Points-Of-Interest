package com.example.android.rest_testing.net

import android.content.Context
import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.retrofit.JWT
import com.example.android.rest_testing.net.retrofit.LoginResponse
import com.example.android.rest_testing.net.retrofit.PlaceResponse
import com.example.android.rest_testing.net.retrofit.SimpleResponse

interface RestInterface {
    fun loginUser(user: UserShort): LoginResponse
    fun registerUser(user: User): SimpleResponse

    fun savePlace(userPlace: UserPlace, context: Context): SimpleResponse
    fun deletePlace(userPlace: UserPlace): SimpleResponse
    fun getSavedPlaces(token:JWT?, fromIndex:Int, toIndex:Int,  keyWord:String?, type:String?): MutableList<PlaceResponse>
}