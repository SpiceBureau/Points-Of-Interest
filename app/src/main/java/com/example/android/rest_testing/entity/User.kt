package com.example.android.rest_testing.entity

import java.io.Serializable

class User(
    var username: String = "",
    var firstName: String = "",
    var lastName: String = "",
    var email: String = "",
    var password: String = ""
): Serializable{
    override fun toString(): String {
        return username + " " + firstName + " " + lastName + " " + email + " " + password
    }
}