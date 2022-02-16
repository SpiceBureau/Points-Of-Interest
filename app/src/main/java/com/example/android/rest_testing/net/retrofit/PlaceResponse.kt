package com.example.android.rest_testing.net.retrofit

class PlaceResponse(
    var id: Int?,
    var name:String = "",
    var type:String = "",
    var locationLatitude:Double = 0.0,
    var locationLongitude:Double = 0.0

) {
    init {
        id = null
    }

    override fun toString(): String {
        return name + " " + type + " " + locationLatitude + " " + locationLongitude
    }
}