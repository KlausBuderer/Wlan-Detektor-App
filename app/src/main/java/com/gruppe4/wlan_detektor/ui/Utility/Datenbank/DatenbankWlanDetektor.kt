package com.gruppe4.wlan_detektor.ui.Utility.Datenbank

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
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

    abstract class DatenbankWlanDetektor : RoomDatabase()
    {
        companion object
        {
            private lateinit var datenbankWlanDetektor: DatenbankWlanDetektor
            fun getDatabase(applicationContext: Context): DatenbankWlanDetektor {
                if (!(::datenbankWlanDetektor.isInitialized)) {
                    datenbankWlanDetektor =
                        Room.databaseBuilder(applicationContext,
                            datenbankWlanDetektor::class.java, "wlandetektor-db")
                            .build()
                }
                return datenbankWlanDetektor
            }
        }
        // noch zu implementieren.
        // abstract fun userDao(): UserDao
        //abstract fun messageDao(): MessageDao
    }

