package com.example.android.rest_testing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class TempActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test)

        supportActionBar?.hide()
    }
}