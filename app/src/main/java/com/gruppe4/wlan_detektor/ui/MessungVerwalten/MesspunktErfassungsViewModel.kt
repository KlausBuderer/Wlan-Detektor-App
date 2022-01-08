package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Application
import android.graphics.Color
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor.ui.Utility.Datum
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.IOException
import java.lang.NullPointerException

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

    private val LOG_TAG = "Messpunkterfassung: "

    private val _signalstaerke = MutableLiveData<Int>().apply {
        value = netzwerkInfo.getConnectionInfo().rssi
    }
    val signalstaerke: LiveData<Int> = _signalstaerke

    private val _progressFarbe = MutableLiveData<Int>().apply {
        value = netzwerkInfo.progressBarFarbeEinstellen()
    }

    val progressBar: LiveData<Int> = _progressFarbe

    private val _messpunkt = MutableLiveData<TblMesspunkt>().apply {

    }

    val messpunkt: LiveData<TblMesspunkt> = _messpunkt


    fun buttonFreigeben(): Boolean{
        return konditionGebaeude && konditionRaumname && konditionStockwerk
    }

    suspend fun getMesspunkt(messpunktId: Long) {
        try {
            val messpunkt = repositoryDb.getMesspunkt(messpunktId)
            Log.e(LOG_TAG, "Messpunkt query erfolgreich $messpunktId")

            _messpunkt.postValue(messpunkt)
        } catch (e: IOException) {
            Log.e(LOG_TAG, "Messpunkt query nicht erfolgreich")
        }

    }
        // Speichern des Messpunkts in der Datenbank
        fun messpunktSpeichern(messpunkt: TblMesspunkt) {
            repositoryDb.insertMesspunkt(messpunkt)
        }

        // Speichern des Messpunkts in der Datenbank
        fun messpunktUpdate(messpunkt: TblMesspunkt) {
            repositoryDb.updateMesspunkt(messpunkt)
        }

        suspend fun startUpdates() {
            withContext(Dispatchers.IO) {
                while (true) {
                    try {
                        netzwerkInfo.refreshInfo()

                        _signalstaerke.postValue(netzwerkInfo.refreshInfo().rssi)
                        _progressFarbe.postValue(netzwerkInfo.progressBarFarbeEinstellen())
                    } catch (e: NullPointerException) {

                    }

                    delay(500)
                }
            }

        }

    }
