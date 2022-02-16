package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Scene
import android.transition.Transition
import android.transition.TransitionManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import kotlinx.android.synthetic.main.test.*

class LoginAndRegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_and_register_scene)

        //for login_and_register_scene layout
        val btnLoginLAR = findViewById<Button>(R.id.btnLoginLAR)
        val btnRegisterLAR = findViewById<Button>(R.id.btnRegisterLAR)

        btnLoginLAR.setOnClickListener {
            val extras = Bundle()
            extras.putString("username", "null")
            val loginActivity = LoginScene()
            val intent = Intent(this, loginActivity::class.java)
            intent.putExtras(extras)
            startActivity(intent)
        }
        btnRegisterLAR.setOnClickListener {
            val registerActivity = RegisterScene()
            val intent = Intent(this, registerActivity::class.java)
            startActivity(intent)
        }
    }
}