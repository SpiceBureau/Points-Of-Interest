package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.constraintlayout.widget.ConstraintLayout

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnEnterApp = findViewById<Button>(R.id.btnEnterApp)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val LoginScreen = findViewById<ConstraintLayout>(R.id.LoginScreen)

        btnEnterApp.setOnClickListener {
            val loadingActivity = LoadingActivity()
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
        }

        btnLogin.setOnClickListener {
            LoginScreen.removeView(it)
        }

        btnRegister.setOnClickListener {
            LoginScreen.removeView(it)
        }
    }
}