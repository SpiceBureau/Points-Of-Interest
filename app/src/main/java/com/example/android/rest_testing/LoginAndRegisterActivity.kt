package com.example.android.rest_testing

import android.R.attr.data
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class LoginAndRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_and_register_scene)

        supportActionBar?.hide()

        //for login_and_register_scene layout
        val btnLoginLAR = findViewById<Button>(R.id.btnLoginLAR)
        val btnRegisterLAR = findViewById<Button>(R.id.btnRegisterLAR)
        val btnOfflineMode = findViewById<Button>(R.id.btnOfflineLogin)

        btnLoginLAR.setOnClickListener {
            val extras = Bundle()
            extras.putString("username", "null")
            val loginActivity = LoginScene()
            val intent = Intent(this, loginActivity::class.java)
            intent.putExtras(extras)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            if (!isTaskRoot){
                finish()
            }
        }
        btnRegisterLAR.setOnClickListener {
            val registerActivity = RegisterScene()
            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left)
            if (!isTaskRoot){
                finish()
            }
        }
        btnOfflineMode.setOnClickListener {
            val loadingActivity = LoadingActivity()
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
            if (!isTaskRoot){
                finish()
            }
        }
    }
}