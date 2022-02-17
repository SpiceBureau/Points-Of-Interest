package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.example.android.rest_testing.entity.UserShort

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val user = intent.getSerializableExtra("user")

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val mainActivity = MainActivity()
            val intent = Intent(this, mainActivity::class.java)
            intent.putExtra("user", user)
            startActivity(intent)
            finish()
        }, 10)
    }
}