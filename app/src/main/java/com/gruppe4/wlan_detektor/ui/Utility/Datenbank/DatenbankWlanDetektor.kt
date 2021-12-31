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
    ],
    version = 1,
    exportSchema = false )

    abstract class DatenbankWlanDetektor : RoomDatabase() {


    companion object {
        @Volatile
        private var INSTANCE: DatenbankWlanDetektor? = null

        fun getInstance(context: Context): DatenbankWlanDetektor {
            synchronized(this) {
                return INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    DatenbankWlanDetektor::class.java,
                    "wlandetektor_db"
                ).build().also {
                    INSTANCE = it
                }
            }
        }
    }
}