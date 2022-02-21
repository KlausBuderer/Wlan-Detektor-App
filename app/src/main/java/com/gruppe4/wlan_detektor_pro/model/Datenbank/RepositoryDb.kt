package com.gruppe4.wlan_detektor_pro.model.Datenbank

import android.app.Application
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.*

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

    fun updateMessungsNamen(messung: String, neuerNamen: String){
        coroutineScope.launch(Dispatchers.IO){
            asyncUpdateMessung(wlanDetektorDao.getDieMessung(messung), neuerNamen)
        }
    }

    private suspend fun asyncUpdateMessung(messung: TblMessung, neuerNamen: String){
        messung.name = neuerNamen
        wlanDetektorDao.updateTblMessung(messung)

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

    suspend fun getHersteller(macadresse: String): String{
        return  withContext(Dispatchers.IO){
            wlanDetektorDao.getHersteller(macadresse)
        }
    }


    suspend fun namenPruefen(namen:String):Int{
      return  wlanDetektorDao?.nameExists(namen)
    }

    suspend fun getMessung(namen: String): TblMessung{
        return coroutineScope.async(Dispatchers.IO) {
            val messung = wlanDetektorDao?.getDieMessung(namen)
            return@async messung


        }.await()
    }

    suspend fun getMessung(id: Long): TblMessung{
        return coroutineScope.async(Dispatchers.IO) {
            val messung = wlanDetektorDao?.getDieMessung(id)
            return@async messung


        }.await()
    }

    suspend fun deleteMessung(messung: TblMessung){
        coroutineScope.launch(Dispatchers.IO){
            wlanDetektorDao.deleteTblMessung(messung)
        }
    }

    suspend fun deleteMesspunkt(id: Long){
        coroutineScope.launch(Dispatchers.IO){
            wlanDetektorDao.deleteTblMesspunkt(getMesspunkt(id))
        }
    }

}