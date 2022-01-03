package com.gruppe4.wlan_detektor.model.Datenbank

import android.app.Application
import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.*
import kotlin.math.absoluteValue

class RepositoryDb(application: Application) {

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




    suspend fun queryMessung(){
        withContext(Dispatchers.IO){
            wlanDetektorDao.getAllMessung()
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



}