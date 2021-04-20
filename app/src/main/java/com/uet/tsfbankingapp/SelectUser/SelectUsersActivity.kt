package com.uet.tsfbankingapp.SelectUser

import android.database.Cursor
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.uet.tsfbankingapp.ModelClasses.UserData
import com.uet.tsfbankingapp.R
import com.uet.tsfbankingapp.SqliteDBs.TransactionHistoryDB
import com.uet.tsfbankingapp.SqliteDBs.UserDB
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class SelectUsersActivity : AppCompatActivity() {

    var users: ArrayList<UserData> = ArrayList()
    var fromUser = arrayOf<Int>(-1,0)
    var toUser = arrayOf<Int>(-1,0)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_users)

        fromUser[0] = intent.getIntExtra("id",0)
        fromUser[1] = intent.getIntExtra("amount",0)

        val toolbar: Toolbar = findViewById(R.id.app_bar_layout) as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.title = "Select User"

        //getting recyclerview from xml
        val recyclerView = findViewById(R.id.user_list) as RecyclerView

        //adding a layoutmanager
        recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        val db: UserDB = UserDB(applicationContext)

        val cursor: Cursor = db.viewAllUsers()

        if (cursor!=null && cursor.moveToFirst()) {

            val id = cursor.getInt(cursor.getColumnIndex("id"))
            val amount = cursor.getInt(cursor.getColumnIndex("amount"))
            val userName = cursor.getString(cursor.getColumnIndex("name"))
            val userEmail = cursor.getString(cursor.getColumnIndex("email"))
            val user= UserData(id, userName, userEmail, amount)
            if(!fromUser[0].equals(id))
            {
                users.add(user)
            }

            while (cursor.moveToNext())
            {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val amount = cursor.getInt(cursor.getColumnIndex("amount"))
                val userName = cursor.getString(cursor.getColumnIndex("name"))
                val userEmail = cursor.getString(cursor.getColumnIndex("email"))
                val user= UserData(id, userName, userEmail, amount)
                if(!fromUser[0].equals(id))
                {
                    users.add(user)
                }
            }
        }

        //creating our adapter
        val adapter =
            UserSelectRecyclerViewAdapter(
                users,
                toUser
            )

        //now adding the adapter to recyclerview
        recyclerView.adapter = adapter

        val amountInput: EditText = findViewById(R.id.enter_amount) as EditText

        val confirmBtn: Button = findViewById(R.id.confirm_btn) as Button

        confirmBtn.setOnClickListener {
            if(amountInput.text.toString().isNotEmpty())
            {
                if(toUser[0]!=-1)
                {
                    val transferAmount: Int = amountInput.text.toString().toInt()
                    if(transferAmount<=fromUser[1])
                    {
                        showPopup(transferAmount,db)
                    }
                    else
                    {
                        Toast.makeText(applicationContext,"Amount is exceeded from the user account!",Toast.LENGTH_SHORT).show()
                    }
                }
                else
                {
                    Toast.makeText(applicationContext,"Please select user first!",Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(applicationContext,"Please enter amount!",Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPopup(amount: Int,db: UserDB){

        val builder = AlertDialog.Builder(this)
        builder.setMessage("Are you sure you want to transfer money?")

        builder.setPositiveButton("No") { dialog, which ->
            dialog.dismiss()
        }

        builder.setNegativeButton("Yes") { dialog, which ->

            fromUser[1] = fromUser[1] - amount
            toUser[1] = toUser[1] + amount

            val check1 = db.updateAmount(toUser[0],toUser[1])
            if(check1)
            {
                val check2 = db.updateAmount(fromUser[0],fromUser[1])
                if(check2)
                {
                    val date = getCurrentDateTime()
                    val dateInString = date.toString("MMM dd, yyyy hh:mm a")

                    val dbTrans: TransactionHistoryDB = TransactionHistoryDB((applicationContext))

                    val check3: Long = dbTrans.insertTransaction(fromUser[0],toUser[0],amount,dateInString)

                    if(!check3.equals(-1))
                    {
                        Toast.makeText(applicationContext,"Transaction successful!",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
                else
                {
                    Toast.makeText(applicationContext,"Transaction failed!",Toast.LENGTH_SHORT).show()
                }
            }
            else
            {
                Toast.makeText(applicationContext,"Transaction failed!",Toast.LENGTH_SHORT).show()
            }

            dialog.dismiss()
        }

        builder.show()
    }

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getCurrentDateTime(): Date {
        return Calendar.getInstance().time
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}