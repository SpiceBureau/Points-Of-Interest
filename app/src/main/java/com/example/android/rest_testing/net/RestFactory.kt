package com.example.android.rest_testing.net

import com.example.android.rest_testing.net.retrofit.RestRetrofit

object RestFactory {
    val BASE_IP = "10.0.2.2"

    val instance: RestInterface
        get() = RestRetrofit()
}