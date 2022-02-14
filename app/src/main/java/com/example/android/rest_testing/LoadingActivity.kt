package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val mainActivity = MainActivity()
            val intent = Intent(this, mainActivity::class.java)
            startActivity(intent)
            finish()
        }, 10)
    }
}