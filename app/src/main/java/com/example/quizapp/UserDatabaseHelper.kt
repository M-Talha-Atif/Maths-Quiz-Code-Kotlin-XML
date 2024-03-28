package com.example.quizapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

// Database helper class using SQLITE
class UserDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // Database version, name, table name and table columns
    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "UserDatabase.db"
        private const val TABLE_NAME = "User"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EMAIL = "email"
        private const val COLUMN_PASSWORD = "password"
    }

    // Create table
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = ("CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, $COLUMN_EMAIL TEXT, $COLUMN_PASSWORD TEXT)")
        db.execSQL(createTable )
    }
    // function to drop table in case of upgrading
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // function to add user in database
    fun addUser(email: String, password: String): String {
        if (!isValidEmail(email)) {
            return "email" // in case of invalid email
        }
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLUMN_EMAIL, email)
        values.put(COLUMN_PASSWORD, password)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return if (success != -1L) "success" else "failure" // return success that means successful signup
    }

    // function to check user entered email to check either it comes under below given regular expression
    private fun isValidEmail(email: String): Boolean {
        // Expression to match email characters to ensure like abc@gmail.com
        val emailRegex = "^[A-Za-z](.*)([@]{1})(.{1,})(\\.)(.{1,})"
        return email.matches(emailRegex.toRegex())
    }


    // function to check user exists in database
    fun checkUser(email: String, password: String): Boolean {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_EMAIL=? AND $COLUMN_PASSWORD=?", arrayOf(email, password))
        val count = cursor.count
        cursor.close()
        db.close()
        return count > 0 // return true in case count is greater than 0
    }
}
