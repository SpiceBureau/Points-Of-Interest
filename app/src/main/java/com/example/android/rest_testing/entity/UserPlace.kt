package com.example.android.rest_testing.entity

import com.example.android.rest_testing.net.retrofit.JWT
import java.io.Serializable

class UserPlace(jwtToken: JWT, savingPlace: Place):Serializable {
    private val token = jwtToken
    private val place = savingPlace

    override fun toString(): String {
        return token.toString() + " " + place.toString()
    }

    public fun getToken(): String{
        return token.toString()
    }

    public fun getPlace(): Place{
        return place
    }
}