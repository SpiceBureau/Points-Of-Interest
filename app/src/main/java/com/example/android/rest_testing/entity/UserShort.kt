package com.example.android.rest_testing.entity

import java.io.Serializable

class UserShort(
    var username: String = "",
    var password: String = ""
):Serializable {

    override fun toString(): String {
        return username + " " + password
    }
}