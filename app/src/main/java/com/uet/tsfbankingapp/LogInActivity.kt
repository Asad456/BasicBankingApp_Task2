package com.uet.tsfbankingapp

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences.Editor
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.uet.tsfbankingapp.AllUsers.AllUsersActivity

class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val Username: String = "admin"
        val Password: String = "admin123"

        val username: TextInputEditText = findViewById(R.id.username) as TextInputEditText
        val password: TextInputEditText = findViewById(R.id.password) as TextInputEditText
        val login: Button = findViewById(R.id.log_in_button) as Button

        login.setOnClickListener {
            if(!username.text.toString().isEmpty())
            {
                if(!password.text.toString().isEmpty())
                {
                    if(username.text.toString().equals(Username) && password.text.toString().equals(Password))
                    {
                        val editor: Editor = applicationContext.getSharedPreferences("CheckLogin", Context.MODE_PRIVATE).edit()
                        editor.putString("Check", "Yes")
                        editor.apply()

                        val intent = Intent(this@LogInActivity, AllUsersActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this@LogInActivity, "Login Successful...", Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Toast.makeText(this@LogInActivity, "Username/Password is incorrect!", Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    password.setError("Please Enter Password")
                }
            }
            else
            {
                username.setError("Please Enter Username")
            }
        }
    }
}