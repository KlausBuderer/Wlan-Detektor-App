package com.gruppe4.wlan_detektor.ui.Visualisierung

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import java.io.IOException

class VisualisierungGridViewModel(application: Application) : AndroidViewModel(application) {

    private val LOG_TAG = "Visu Grid View: "
    private val repositoryDb = RepositoryDb(application)


    private val _messpunkte = MutableLiveData<List<TblMesspunkt>>()
    val messpunkte: LiveData<List<TblMesspunkt>> = _messpunkte

    private val _messung = MutableLiveData<TblMessung>()
    val messung: LiveData<TblMessung> = _messung

    suspend fun getMesspunkte(messungsId: Long){
        try {
            val aktuelleMesspunkte = repositoryDb.getMesspunkte(messungsId)
            Log.d(LOG_TAG, "Messpunkt query erfolgreich")

            _messpunkte.postValue(aktuelleMesspunkte)

        }catch (e: IOException){
            Log.d(LOG_TAG, "Messpunkt query nicht erfolgreich")
        }

    }

    suspend fun getMessung(id: Long){
        try {
            val aktuelleMessung = repositoryDb.getMessung(id)
            Log.d(LOG_TAG, "Query erfolgreich")

            _messung.postValue(aktuelleMessung)
        }catch (e: IOException){
            Log.d(LOG_TAG, "Query nicht erfolgreich")
        }
    }



}