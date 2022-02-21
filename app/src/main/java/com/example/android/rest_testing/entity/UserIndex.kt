package com.example.android.rest_testing.entity

import com.example.android.rest_testing.net.retrofit.JWT
import java.io.Serializable

class UserIndex(jwtToken: JWT, startIndex: Int, endIndex: Int):Serializable{
    private val token = jwtToken
    val fromIndex = startIndex
    val toIndex = endIndex

    override fun toString(): String {
        return token.toString() + " " + fromIndex + " " + toIndex
    }

    fun getToken(): String{
        return token.toString()
    }

}