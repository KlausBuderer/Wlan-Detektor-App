package com.gruppe4.wlan_detektor.model.Datenbank

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.*
import kotlin.math.absoluteValue

class RepositoryDb(application: Application) {

    private val LOG_TAG: String = "RepositoryDb: "

    private val wlanDetektorDao:WlanDetektorDao
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    init {
    val db = WlanDetektorDb.createInstance(application)
    wlanDetektorDao = db.wlanDetektorDao

    }

    fun insertMessung(messung: TblMessung){
        coroutineScope.launch (Dispatchers.IO) {
            asyncInsertMessung(messung)
        }
    }

    private suspend fun asyncInsertMessung(tblMessung: TblMessung){
        wlanDetektorDao.insertTblMessung(tblMessung)
    }


    fun insertMesspunkt(messpunkt: TblMesspunkt){
        coroutineScope.launch (Dispatchers.IO) {
            asyncInsertMesspunkt(messpunkt)
        }
    }

    private suspend fun asyncInsertMesspunkt(tblMesspunkt: TblMesspunkt){
        wlanDetektorDao.insertTblMesspunkt(tblMesspunkt)
    }

    fun updateMesspunkt(messpunkt: TblMesspunkt){
        coroutineScope.launch(Dispatchers.IO) {
            asyncUpdateMesspunkt(messpunkt)
        }
    }

    private suspend fun asyncUpdateMesspunkt(messpunkt: TblMesspunkt){
        wlanDetektorDao.updateTblMesspunkt(messpunkt)
    }


    suspend fun queryMessung(): List<TblMessung>{
      return  withContext(Dispatchers.IO){
            wlanDetektorDao.getAllMessung()
        }
    }

    suspend fun getMesspunkte(messungsId: Long): List<TblMesspunkt>{
        return  withContext(Dispatchers.IO){
            wlanDetektorDao.getAllMesspunkte(messungsId)
        }
    }

    suspend fun getMesspunkt(messpunktId: Long): TblMesspunkt{
        return  withContext(Dispatchers.IO){
            wlanDetektorDao.getMesspunkt(messpunktId)
        }
    }


 /*   suspend fun namenPruefen(namen: String): Boolean {
        var resultat: Int? = -1

        coroutineScope.launch (Dispatchers.Main) {
           resultat = asyncNamenPruefen(namen).await()
        }
        return resultat == -1
    }*/

    fun namenPruefen(namen: String): Deferred<Int?> =
        coroutineScope.async(Dispatchers.IO) {
            return@async wlanDetektorDao?.nameExists(namen)
        }

    suspend fun getMessung(namen: String): TblMessung{
        return coroutineScope.async(Dispatchers.IO) {
            val messung = wlanDetektorDao?.getDieMessung(namen)
            return@async messung


        }.await()
    }

}