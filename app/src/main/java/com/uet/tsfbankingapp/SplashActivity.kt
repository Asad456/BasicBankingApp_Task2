package com.uet.tsfbankingapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.uet.tsfbankingapp.AllUsers.AllUsersActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val prefs = getSharedPreferences("CheckLogin", Context.MODE_PRIVATE)
        val check = prefs.getString("Check", "No")

        Handler().postDelayed(
                {
                    if(check.equals("Yes"))
                    {
                        val intent = Intent(this@SplashActivity, AllUsersActivity::class.java)
                        startActivity(intent)
                    }
                    else
                    {
                        val intent = Intent(this@SplashActivity, LogInActivity::class.java)
                        startActivity(intent)
                    }
                },
                1500 // value in milliseconds
        )

    }
}