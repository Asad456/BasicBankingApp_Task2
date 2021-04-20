package com.uet.tsfbankingapp.SqliteDBs

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor

//creating the database logic, extending the SQLiteOpenHelper base class
class TransactionHistoryDB(context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "TransactionDatabase"
        private val TABLE_NAME = "TransactionTable"
        private val KEY_ID = "id"
        private val FROM = "from_user"
        private val TO = "to_user"
        private val AMOUNT = "amount"
        private val DATE_TIME = "date_time"
    }
    override fun onCreate(db: SQLiteDatabase?) {
        // TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + AMOUNT + " INTEGER," + DATE_TIME + " TEXT," + FROM + " INTEGER,"
                + TO + " INTEGER" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    //method to insert data
    fun insertTransaction(from: Int, to: Int, amount: Int, date_time: String):Long{

        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(AMOUNT, amount)
        contentValues.put(DATE_TIME, date_time)
        contentValues.put(FROM, from)
        contentValues.put(TO, to)

        // Inserting Row
        val success = db.insert(TABLE_NAME, null, contentValues)
        db.close() // Closing database connection
        return success
    }

    //method to read data
    fun viewAllTransactions(): Cursor{

        val selectQuery = "SELECT * FROM $TABLE_NAME"
        val db = this.readableDatabase

        var cursor: Cursor? = null
        cursor = db.rawQuery(selectQuery, null)

        return cursor
    }
}