package com.example.android.rest_testing.net

import com.example.android.rest_testing.net.retrofit.RestRetrofit

object RestFactory {
    val BASE_IP = "192.168.0.106"

    //val BASE_IP = "192.168.100.32" //za isprobavanje na mobitelu

    val instance: RestInterface
        get() = RestRetrofit()
}