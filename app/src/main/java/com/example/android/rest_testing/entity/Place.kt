package com.example.android.rest_testing.entity

import java.io.Serializable

class Place(
    var name:String = "",
    var type:String = "",
    var locationLatitude:Double = 0.0,
    var locationLongitude:Double = 0.0
):Serializable{
    override fun toString(): String {
        return name + " " + type + " " + locationLatitude + " " + locationLongitude
    }
}