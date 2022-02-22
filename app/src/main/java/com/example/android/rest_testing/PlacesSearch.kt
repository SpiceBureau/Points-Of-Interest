package com.example.android.rest_testing

class PlacesSearch(
    var keyWord:String?,
    var typeOfPlace:String?
){
    override fun toString(): String {
        return keyWord + " " + typeOfPlace
    }
}