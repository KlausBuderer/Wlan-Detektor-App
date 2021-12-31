package com.gruppe4.wlan_detektor.ui.Utility.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

            companion object {
                private const val DATABASE_VERSION = 1
                private const val DATABASE_NAME = "WlanDetektor"

            // Tabelle xx

            // Tabelle xx

            // Tabelle xx

            // Tabelle xx

            // Tabelle xx

            // Tabelle xx

            }

    override fun onCreate(db: SQLiteDatabase?) {
        TODO("Not yet implemented")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}