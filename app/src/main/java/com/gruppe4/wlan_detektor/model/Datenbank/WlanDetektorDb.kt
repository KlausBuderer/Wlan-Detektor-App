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
        TblMesspunkt::class,
        TblHersteller::class,
        TblMessung::class], version = 1, exportSchema = false
)

abstract class WlanDetektorDb : RoomDatabase() {
    abstract val wlanDetektorDao: WlanDetektorDao

    companion object {
        @Volatile
        private var INSTANCE: WlanDetektorDb? = null

        fun createInstance(application: Application): WlanDetektorDb {
            synchronized(this)
            {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        application.applicationContext,
                        WlanDetektorDb::class.java,
                        "wlan_detektor_db"
                    ).createFromAsset("datenbank/wlan_detektor_db.db").build()
                }
                return instance
            }
        }
    }
}

