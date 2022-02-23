package com.example.android.rest_testing

object PlacesSearch{
    var keyWord:String? = null
    var typeOfPlace:String? = null

    override fun toString(): String {
        return keyWord + " " + typeOfPlace
    }
}