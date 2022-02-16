package com.example.android.rest_testing.net.retrofit

import com.example.android.rest_testing.entity.Place
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserPlace
import retrofit.http.Body
import retrofit.http.GET
import retrofit.http.Headers
import retrofit.http.POST

interface PlaceService {
    @POST("/addPlace")
    fun savePlace(@Body userPlace: UserPlace): PlaceResponse

    @POST("/getPlaces")
    fun getPlaces(@Body userIndex: UserIndex): MutableList<PlaceResponse>
}