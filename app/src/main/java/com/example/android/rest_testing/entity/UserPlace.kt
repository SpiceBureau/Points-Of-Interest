package com.example.android.rest_testing.entity

import java.io.Serializable

class UserPlace(userShort: UserShort, savingPlace: Place):Serializable {
    private val user = userShort
    private val place = savingPlace

    override fun toString(): String {
        return user.toString() + " " + place.toString()
    }
}