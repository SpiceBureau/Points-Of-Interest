package com.example.android.rest_testing.entity

import java.io.Serializable

class UserIndex(userShort: UserShort, startIndex: Int, endIndex: Int):Serializable{
    private val user = userShort
    private val fromIndex = startIndex
    private val toIndex = endIndex

    override fun toString(): String {
        return user.toString() + " " + fromIndex + " " + toIndex
    }

}