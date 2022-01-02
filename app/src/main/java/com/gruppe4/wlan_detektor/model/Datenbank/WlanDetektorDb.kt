package com.gruppe4.wlan_detektor.model.Datenbank

import android.app.Application
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.*

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
        abstract val wlanDetektorDao: WlanDetektorDao

        companion object
        {
           /* private lateinit var wlanDetektorDb: WlanDetektorDb
            fun getDatabase(applicationContext: Context): WlanDetektorDb {
                if (!(::wlanDetektorDb.isInitialized)) {
                    wlanDetektorDb =
                        Room.databaseBuilder(applicationContext,
                            wlanDetektorDb::class.java, "wlandetektor-db")
                            .build()
                }
                return wlanDetektorDb*/
           @Volatile
           private var INSTANCE:WlanDetektorDb? = null

            fun createInstance(application: Application):WlanDetektorDb {
                synchronized(this)
                {
                    var instance = INSTANCE
                    if (instance == null) {
                        instance = Room.databaseBuilder(
                            application.applicationContext,
                            WlanDetektorDb::class.java,
                            "wlan_detektor_db"
                        )
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                    return instance
                }
            }
        }
        // *********** tbd **************
        // abstract fun userDao(): UserDao
        //abstract fun messageDao(): MessageDao
    }

