package com.gruppe4.wlan_detektor.ui.Utility.Datenbank

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.*

@Database(
    entities = [
        TblMasseinheit::class,
        TblMesspunkt::class,
        TblMessung::class,
        TblMessungRelation::class,
        TblRaeumlichkeiten::class,
        TblStockwerk::class
    ], version = 1, exportSchema = false )

    abstract class WlanDetektorDb : RoomDatabase()
    {
        companion object
        {
            private lateinit var wlanDetektorDb: WlanDetektorDb
            fun getDatabase(applicationContext: Context): WlanDetektorDb {
                if (!(::wlanDetektorDb.isInitialized)) {
                    wlanDetektorDb =
                        Room.databaseBuilder(applicationContext,
                            wlanDetektorDb::class.java, "wlandetektor-db")
                            .build()
                }
                return wlanDetektorDb
            }
        }
        // *********** tbd **************
        // abstract fun userDao(): UserDao
        //abstract fun messageDao(): MessageDao
    }

