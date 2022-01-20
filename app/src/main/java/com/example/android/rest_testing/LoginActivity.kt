package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnEnterApp = findViewById<Button>(R.id.btnEnterApp)

        btnEnterApp.setOnClickListener {
            val loadingActivity = LoadingActivity()
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
        }
    }
}