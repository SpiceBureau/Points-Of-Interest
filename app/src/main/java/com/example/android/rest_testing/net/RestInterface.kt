package com.example.android.rest_testing.net

import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserPlace
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.retrofit.PlaceResponse

interface RestInterface {
    fun loginUser(user: UserShort): Boolean
    fun registerUser(user: User): Boolean

    fun savePlace(userPlace: UserPlace): Boolean
    fun getSavedPlaces(userIndex: UserIndex): MutableList<PlaceResponse>
    fun deletePlace(userPlace: UserPlace): Boolean
}