package com.example.android.rest_testing.net.retrofit

class UserResponse(
    var id: Int?,
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var username: String = "",
    var password: String = ""
){
    init {
        id = null
    }

    override fun toString(): String {
        return id.toString() + " " + firstName + " " + lastName
    }
}