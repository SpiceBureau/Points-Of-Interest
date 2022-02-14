package com.example.android.rest_testing.net

import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.entity.UserShort

interface RestInterface {
    fun loginUser(user: UserShort): Boolean
    fun registerUser(user: User): Boolean
}