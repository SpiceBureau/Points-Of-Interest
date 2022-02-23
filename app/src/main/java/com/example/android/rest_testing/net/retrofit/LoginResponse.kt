package com.example.android.rest_testing.net.retrofit

class LoginResponse(
    var jwtToken:JWT?,
    var passed:Boolean,
    var status:Int
)