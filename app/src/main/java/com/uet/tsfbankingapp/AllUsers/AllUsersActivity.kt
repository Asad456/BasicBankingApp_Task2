package com.uet.tsfbankingapp.AllUsers

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uet.tsfbankingapp.LogInActivity
import com.uet.tsfbankingapp.ModelClasses.UserData
import com.uet.tsfbankingapp.R
import com.uet.tsfbankingapp.Transaction.TransactionHistoryActivity
import com.uet.tsfbankingapp.SqliteDBs.UserDB

class AllUsersActivity : AppCompatActivity() {

    var users: ArrayList<UserData> = ArrayList()
    var adapter: UserRecyclerViewAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val toolbar: Toolbar = findViewById(R.id.app_bar_layout) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = "TSF Banking App"

        val prefs = getSharedPreferences("CheckDB", Context.MODE_PRIVATE)
        val check = prefs.getString("Check", "Insert")

        if(check.equals("Insert"))
        {
            val db: UserDB = UserDB(applicationContext)
            db.insertUsers("Asad","asad@gmail.com",1000)
            db.insertUsers("Ali","ali@gmail.com",500)
            db.insertUsers("Ahmad","ahmad@gmail.com",3000)
            db.insertUsers("Hamza","hamza@gmail.com",1500)
            db.insertUsers("Bilal","bilal@gmail.com",1200)
            db.insertUsers("Saad","saad@gmail.com",1700)
            db.insertUsers("Arslan","arslan@gmail.com",800)
            db.insertUsers("Osama","osama@gmail.com",2000)
            db.insertUsers("Rehan","rehan@gmail.com",900)
            db.insertUsers("Awais","awais@gmail.com",1000)

            val editor: SharedPreferences.Editor = applicationContext.getSharedPreferences("CheckDB", Context.MODE_PRIVATE).edit()
            editor.putString("Check", "Not Insert")
            editor.apply()
        }

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.user_list) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        //creating our adapter
        adapter = UserRecyclerViewAdapter(users)

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()

        users.clear()

        val db: UserDB = UserDB(applicationContext)
        val cursor: Cursor = db.viewAllUsers()

        if (cursor!=null && cursor.moveToFirst()) {

            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val amount = cursor.getInt(cursor.getColumnIndex("amount"))
            val userName = cursor.getString(cursor.getColumnIndex("name"))
            val userEmail = cursor.getString(cursor.getColumnIndex("email"))
            val user= UserData(
                id,
                userName,
                userEmail,
                amount
            )
            users.add(user)

            while (cursor.moveToNext())
            {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val amount = cursor.getInt(cursor.getColumnIndex("amount"))
                val userName = cursor.getString(cursor.getColumnIndex("name"))
                val userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val user= UserData(
                    id,
                    userName,
                    userEmail,
                    amount
                )
                users.add(user)
            }
        }

        adapter!!.notifyDataSetChanged()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.options_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.getItemId()
        return when (id) {
            R.id.view_history -> {
                val intent = Intent(this@AllUsersActivity, TransactionHistoryActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.logout_option -> {
                val editor: SharedPreferences.Editor = applicationContext.getSharedPreferences("CheckLogin", Context.MODE_PRIVATE).edit()
                editor.putString("Check", "No")
                editor.apply()
                val intent = Intent(this@AllUsersActivity, LogInActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}