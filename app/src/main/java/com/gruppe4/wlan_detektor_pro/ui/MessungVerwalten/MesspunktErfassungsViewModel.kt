package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor_pro.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor_pro.ui.Utility.Datum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.NullPointerException

/**
 * ## MesspunktErfassungs ViewModel
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MesspunktErfassungsViewModel(application: Application) : AndroidViewModel(application) {

    var messungsId: Long = 0
    private val repositoryDb: RepositoryDb = RepositoryDb(application)
    private val netzwerkInfo: NetzwerkInfo = NetzwerkInfo(application)
    private val datumKlasse: Datum = Datum()
    var datum = datumKlasse.getDatum()
    var zeit = datumKlasse.getZeit()
    var konditionGebaeude: Boolean = false
    var konditionStockwerk: Boolean = false
    var konditionRaumname: Boolean = false
    var konditionMessung: Boolean = false

    private val _messpunkte = MutableLiveData<List<TblMesspunkt>>()
    val messpunkte: LiveData<List<TblMesspunkt>> = _messpunkte

    private val LOG_TAG = "Messpunkterfassung: "

    private val _signalstaerke = MutableLiveData<Int>().apply {
    }
    val signalstaerke: LiveData<Int> = _signalstaerke

    private val _progressFarbe = MutableLiveData<Int>().apply {
    }

    val progressBar: LiveData<Int> = _progressFarbe

    private val _messpunkt = MutableLiveData<TblMesspunkt>().apply {
    }

    val messpunkt: LiveData<TblMesspunkt> = _messpunkt

    /**
     * Setzt Freigabe für den Speicherbutton
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun buttonFreigeben(): Boolean{
        return konditionGebaeude && konditionRaumname && konditionStockwerk && konditionMessung
    }

    /**
     * Liest Messpunkt aus der Datenbank
     * @author Klaus Buderer
     * @param messpunktId Id des Messpunkts
     * @since 1.0.0
     */
    suspend fun getMesspunkt(messpunktId: Long) {
        try {
            val messpunkt = repositoryDb.getMesspunkt(messpunktId)
            Log.d(LOG_TAG, "Messpunkt query erfolgreich $messpunktId")

            _messpunkt.postValue(messpunkt)
        } catch (e: IOException) {
            Log.d(LOG_TAG, "Messpunkt query nicht erfolgreich")
        }
    }

    /**
     * Liest alle Messpunkte einer spezifischen Messung
     * @author Klaus Buderer
     * @since 1.0.0
     * @param messungsId Id der Messung
     */
    suspend fun getMesspunkte(messungsId: Long){
        try {
            val aktuelleMesspunkte = repositoryDb.getMesspunkte(messungsId)
            Log.d(LOG_TAG, "messpunkt query erfolgreich")

            _messpunkte.postValue(aktuelleMesspunkte)
        }catch (e: IOException){
            Log.d(LOG_TAG, "Messpunkt query nicht erfolgreich")
        }
    }

    /**
     * Löscht Messpunkt aus der Datenbank
     * @author Klaus Buderer
     * @since 1.0.0
     * @param id Id des Messpunkts
     */
    suspend fun deleteMesspunkt(id: Long){
        try {
            repositoryDb.deleteMesspunkt(id)
            Log.d(LOG_TAG, "Query erfolgreich")

        }catch (e: IOException){
            Log.d(LOG_TAG, "Query nicht erfolgreich")
        }
    }

    /**
     * Speichern des Messpunkts in der Datenbank
     * @author Klaus Buderer
     * @since 1.0.0
     * @param messpunkt Objekt des Typs TblMesspunkt
     * @see TblMesspunkt
     */
        fun messpunktSpeichern(messpunkt: TblMesspunkt) {
            repositoryDb.insertMesspunkt(messpunkt)
        }

    /**
     * Update des Messpunkts in der Datenbank
     * @author Klaus Buderer
     * @since 1.0.0
     * @param messpunkt Objekt des Typs TblMesspunkt
     * @see TblMesspunkt
     */
        fun messpunktUpdate(messpunkt: TblMesspunkt) {
            repositoryDb.updateMesspunkt(messpunkt)
        }

    /**
     * Startet Messung der Signalstärke des Wlan Netzwerks
     * @author Klaus Buderer
     * @since 1.0.0
     * @see NetzwerkInfo
     */
        suspend fun startUpdates() {
            withContext(Dispatchers.IO) {
                while (true) {
                    try {
                        netzwerkInfo.refreshInfo()

                        _signalstaerke.postValue(netzwerkInfo.refreshInfo().rssi)
                        _progressFarbe.postValue(netzwerkInfo.progressBarFarbeEinstellen())
                    } catch (e: NullPointerException) {
                        Log.e("Messpunkt Erfassen:", "Aufruf Netzwerkinfo nicht erfolgreich")
                    }
                    delay(500)
                }
            }
        }
    }
