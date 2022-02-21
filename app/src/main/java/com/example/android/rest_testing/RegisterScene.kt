package com.example.android.rest_testing

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.net.RestFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterScene : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_scene)

        supportActionBar?.hide()

        val etEnterFirstName = findViewById<EditText>(R.id.etEnterFirstName)
        val etEnterLastName = findViewById<EditText>(R.id.etEnterLastName)
        val etEmail = findViewById<EditText>(R.id.etEnterEmail)
        val etEnterUsernameReg = findViewById<EditText>(R.id.etEnterUsernameReg)
        val etEnterPasswordReg = findViewById<EditText>(R.id.etEnterPasswordReg)
        val etReEnterPasswordReg = findViewById<EditText>(R.id.etReEnterPassword)
        val btnRegisterReg = findViewById<Button>(R.id.btnRegisterReg)
        val btnBackArrowReg = findViewById<ImageButton>(R.id.ibBackArrowReg)

        btnBackArrowReg.setOnClickListener {
            val loginAndRegisterActivity = LoginAndRegisterActivity()
            val intent = Intent(this, loginAndRegisterActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
            finish()
        }

        btnRegisterReg.setOnClickListener {
            val username = etEnterUsernameReg.text.toString()
            val firstName = etEnterFirstName.text.toString()
            val lastName = etEnterLastName.text.toString()
            val email = etEmail.text.toString()
            val password1 = etEnterPasswordReg.text.toString()
            val password2 = etReEnterPasswordReg.text.toString()

            if(username == "" || firstName == "" || lastName == "" || email == "" || password1 == "" || password2 == "" ){
                val toast = Toast.makeText(this, "All fields must be filled.", Toast.LENGTH_LONG)
                toast.show()
            }
            else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                val toast = Toast.makeText(this, "Email address is invalid.", Toast.LENGTH_LONG)
                toast.show()
            }
            else if(password1.length < 6){
                val toast = Toast.makeText(this, "Password must be at least 6 characters long.", Toast.LENGTH_LONG)
                toast.show()
            }
            else if(password1 != password2) {
                val toast = Toast.makeText(this, "Re-entered password is not matching.", Toast.LENGTH_LONG)
                toast.show()
            }
            else{
                CoroutineScope(Dispatchers.IO).launch {
                    val user = User(username, firstName, lastName, email, password1)
                    val rest = RestFactory.instance
                    val result = rest.registerUser(user)
                    withContext(Dispatchers.Main){
                        if(result){
                            val extras = Bundle()
                            extras.putString("username", "null")
                            val loginActivity = LoginScene()
                            val intent = Intent(this@RegisterScene, loginActivity::class.java)
                            intent.putExtras(extras)
                            startActivity(intent)
                            Toast.makeText(this@RegisterScene, "Registration successful! Check email for validation.", Toast.LENGTH_SHORT).show()
                            finish()
                        }
                        else{
                            val toast = Toast.makeText(this@RegisterScene, "Registration failed: username or email are already used.", Toast.LENGTH_LONG)
                            toast.show()
                        }
                    }
                }
            }
        }
    }
}