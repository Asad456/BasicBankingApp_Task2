package com.uet.tsfbankingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.uet.tsfbankingapp.SqliteDBs.UserDB

class AddUser : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_user)

        val toolbar: Toolbar = findViewById(R.id.app_bar_layout) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "Add User"

        val userName = findViewById(R.id.username) as EditText
        val userEmail  = findViewById(R.id.useremail) as EditText
        val amount  = findViewById(R.id.amount) as EditText
        val add  = findViewById(R.id.add_button) as Button

        val db: UserDB = UserDB(applicationContext)

        add.setOnClickListener {
            val check: Long = db.insertUsers(userName.text.toString(),userEmail.text.toString(),Integer.valueOf(amount.text.toString()))

            Toast.makeText(applicationContext, check.toString(), Toast.LENGTH_LONG).show()

        }
    }
}