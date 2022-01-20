package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper

class IntroScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro_screen)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val loginActivity = LoginActivity()
            val intent = Intent(this, loginActivity::class.java)
            startActivity(intent)
            finish()
        }, 1000)

    }
}