package com.example.food4u.sql

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context?) :
    SQLiteOpenHelper(context, "Login.db", null, 1) {
    override fun onCreate(MyDB: SQLiteDatabase) {
        MyDB.execSQL("create Table users(username TEXT primary key, password TEXT)")
    }

    override fun onUpgrade(MyDB: SQLiteDatabase, i: Int, i1: Int) {
        MyDB.execSQL("drop Table if exists users")
    }


    fun insertData(username: String?, password: String?): Boolean {
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("username", username)
        contentValues.put("password", password)
        val result = MyDB.insert("users", null, contentValues)

        return if (result == -1L) false else true
    }

    fun checkusername(username: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery("Select * from users where username = ?", arrayOf(username))
        return if (cursor.count > 0) true else false
    }

    fun checkusernamepassword(username: String, password: String): Boolean {
        val MyDB = this.writableDatabase
        val cursor = MyDB.rawQuery(
            "Select * from users where username = ? and password = ?",
            arrayOf(username, password)
        )
        return if (cursor.count > 0) true else false
    }

    fun readUser(): String{
        val MyDB = this.readableDatabase
        val cursor = MyDB.query("users", null, null, null, null, null, null)
        var stringC:String= cursor.count.toString()
        return stringC
    }

    fun changePassword(username: String, newPassword: String): Boolean{
        val MyDB = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put("password", newPassword)
        val result = MyDB.update("users", contentValues, username +"=?", arrayOf(username.toString())).toLong()
        MyDB.close()
        return Integer.parseInt("$result") != -1
    }

    companion object {
        const val DBNAME = "Login.db"
    }
}