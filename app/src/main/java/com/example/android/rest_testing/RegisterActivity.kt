package com.example.android.rest_testing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.android.rest_testing.entity.User
import com.example.android.rest_testing.net.RestFactory
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        registerUserButton.setOnClickListener {
            val username = usernameRegistrationEditText.text.toString()
            val firstName = firstNameRegistrationEditText.text.toString()
            val lastName = lastNameRegistrationEditText.text.toString()
            val email = emailRegistrationEditText.text.toString()
            val password1 = registrationPasswordEditText.text.toString()
            val password2 = registrationPasswordEditText2.text.toString()

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
                            val loadingActivity = LoadingActivity()
                            val intent = Intent(this@RegisterActivity, loadingActivity::class.java)
                            startActivity(intent)
                        }
                        else{
                            val toast = Toast.makeText(this@RegisterActivity, "Registration failed: username or email are already used.", Toast.LENGTH_LONG)
                            toast.show()
                        }
                    }
                }
            }
        }
    }
}