package com.example.android.rest_testing.net

import com.example.android.rest_testing.net.retrofit.RestRetrofit

object RestFactory {
    val BASE_IP = "192.168.100.32"
//    val BASE_IP = "" //za isprobavanje na mobitelu

    val instance: RestInterface
        get() = RestRetrofit()
}