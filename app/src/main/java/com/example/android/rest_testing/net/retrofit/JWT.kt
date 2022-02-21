package com.example.android.rest_testing.net.retrofit

import java.io.Serializable

class JWT(
    var token: String = ""
): Serializable {
    override fun toString(): String {
        return "Bearer " + token
    }
}