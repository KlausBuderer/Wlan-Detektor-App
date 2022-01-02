package com.gruppe4.wlan_detektor.model.Datenbank

import android.app.Application
import android.content.Context
import com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RepositoryDb(application: Application) {

    private val wlanDetektorDao:WlanDetektorDao

    init {
    val db = WlanDetektorDb.createInstance(application)
    wlanDetektorDao = db.wlanDetektorDao

    }

    suspend fun insertMessung(tblMessung: TblMessung){
        withContext(Dispatchers.IO){
            wlanDetektorDao.insertTblMessung(tblMessung)
        }
    }

    suspend fun queryMessung(){
        withContext(Dispatchers.IO){
            wlanDetektorDao.getAllMessung()
        }
    }

}