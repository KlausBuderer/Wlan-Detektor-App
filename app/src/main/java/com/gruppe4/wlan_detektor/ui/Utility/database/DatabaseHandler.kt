package com.gruppe4.wlan_detektor.ui.Utility.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context) :
        SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION){

            companion object {

                // Datenbank
                private const val DATABASE_VERSION = 1
                private const val DATABASE_NAME = "DbWlanDetektor"

                // Tabellen
                private const val TBL_MESSUNG = "tblMessung"
                private const val TBL_MESSPUNKT = "tblMesspunkt"
                private const val TBL_MESSPUNKTRELATION = "tblMesspunktRelation"
                private const val TBL_RAEUMLICHKEIT = "tblMesspunkt"
                private const val TBL_STOCKWERK = "tblStockwerk"
                private const val TBL_MASSEINHEIT = "tblMassEinheit"

                // Tabelleninhalt zu Tabelle tblMesspunkt
                private const val ID_MESSUNG = "ID"

            }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TBL_MESSUNG = ("CREATE TABLE " + TBL_MESSPUNKT + "("
                + ID_MESSUNG + "INTEGER PRIMARY KEY)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }
}