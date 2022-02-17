package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.android.rest_testing.entity.UserShort
import com.example.android.rest_testing.net.RestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginScene : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_scene)

        supportActionBar?.hide()

        val btnLoginLog = findViewById<Button>(R.id.btnLoginLog)
        val btnBackArrowLog = findViewById<ImageButton>(R.id.ibBackArrowLog)
        val etEnterUsernameLog = findViewById<EditText>(R.id.etEnterUsernameLog)
        val etEnterPasswordLog = findViewById<EditText>(R.id.etEnterPasswordLog)

        val bundle = intent.extras
        val usernameCheck = bundle!!.getString("username")
        if (usernameCheck != "null"){
            etEnterUsernameLog.setText(usernameCheck, TextView.BufferType.EDITABLE)
        }

        btnBackArrowLog.setOnClickListener { // back button
            val loginAndRegisterActivity = LoginAndRegisterActivity()
            val intent = Intent(this, loginAndRegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        btnLoginLog.setOnClickListener {
            val userName = etEnterUsernameLog.text.toString()
            val password = etEnterPasswordLog.text.toString()
            if(userName == "" || password == ""){
                val toast = Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_LONG)
                toast.show()
            }
            else {
                CoroutineScope(Dispatchers.IO).launch {
                    val user = UserShort(userName, password)
                    val rest = RestFactory.instance
                    val result = rest.loginUser(user)
                    withContext(Dispatchers.Main) {
                        if (result) {
                            val loadingActivity = LoadingActivity()
                            val intent = Intent(this@LoginScene, loadingActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            val toast = Toast.makeText(
                                this@LoginScene,
                                "Login failed: wrong username or password.",
                                Toast.LENGTH_LONG
                            )
                            toast.show()
                        }
                    }
                }
            }
        }
    }
}