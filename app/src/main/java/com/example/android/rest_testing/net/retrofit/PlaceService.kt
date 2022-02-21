package com.example.android.rest_testing.net.retrofit

import com.example.android.rest_testing.entity.Place
import com.example.android.rest_testing.entity.UserIndex
import com.example.android.rest_testing.entity.UserPlace
import retrofit.http.*

interface PlaceService {
    @POST("/addPlace")
    fun savePlace(@Header("Authorization") token:String,  @Body place: Place): PlaceResponse

    @GET("/getPlaces")
    fun getPlaces(@Header("Authorization") token:String, @Query("fromIndex") fromIndex:String, @Query("toIndex") toIndex:String): MutableList<PlaceResponse>

    @DELETE("/removePlace")
    fun deletePlace(@Body userPlace: UserPlace): PlaceResponse
}