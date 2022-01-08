package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor.model.Datenbank.WlanDetektorDb
import java.io.IOException

class MessungBearbeitenViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG: String = "Messung bearbeiten"
    private val repositoryDb: RepositoryDb = RepositoryDb(application)


    private val _messung = MutableLiveData<TblMessung>().apply {

    }

    val messung: LiveData<TblMessung> = _messung

    private val _messpunkte = MutableLiveData<List<TblMesspunkt>>()
    val messpunkte: LiveData<List<TblMesspunkt>> = _messpunkte


    suspend fun getMessung(name:String){
        try {
            val aktuelleMessung = repositoryDb.getMessung(name)
            Log.e(LOG_TAG, "Query erfolgreich")

            _messung.postValue(aktuelleMessung)
        }catch (e: IOException){
            Log.e(LOG_TAG, "Query nicht erfolgreich")
        }
    }

    suspend fun getMesspunkte(messungsId: Long){
        try {
            val aktuelleMesspunkte = repositoryDb.getMesspunkte(messungsId)
            Log.e(LOG_TAG, "messpunkt query erfolgreich")

            _messpunkte.postValue(aktuelleMesspunkte)
        }catch (e: IOException){
            Log.e(LOG_TAG, "Messpunkt query nicht erfolgreich")
        }

    }

}

