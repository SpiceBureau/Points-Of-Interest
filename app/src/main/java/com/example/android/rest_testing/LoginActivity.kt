package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val btnEnterApp = findViewById<Button>(R.id.btnEnterApp)
//        val btnLogin = findViewById<Button>(R.id.btnLogin)
//        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val LoginScreen = findViewById<ConstraintLayout>(R.id.LoginScreen)

        btnEnterApp.setOnClickListener {
            val loadingActivity = LoadingActivity()
            val intent = Intent(this, loadingActivity::class.java)
            startActivity(intent)
        }

        loginButton.setOnClickListener {
            val userName = userNameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val user = UserShort(userName, password)
            val rest = RestFactory.instance
            CoroutineScope(Dispatchers.IO).launch {
                val result = rest.loginUser(user)
                withContext(Dispatchers.Main){
                    if(result){
                        val loadingActivity = LoadingActivity()
                        val intent = Intent(this@LoginActivity, loadingActivity::class.java)
                        startActivity(intent)
                    }
                    else{
                        val toast = Toast.makeText(this@LoginActivity, "Login failed: wrong username or password.", Toast.LENGTH_LONG)
                        toast.show()
                    }
                }
            }

        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}