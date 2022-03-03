package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Application
import android.net.wifi.WifiInfo
import android.util.Log
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor_pro.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor_pro.ui.Utility.Datum
import kotlinx.coroutines.*
import java.lang.NullPointerException

class MessungHinzufuegenViewModel(application: Application) : AndroidViewModel(application) {


    private val wifiKlasse: NetzwerkInfo = NetzwerkInfo(application)
    private var connectionInfo: WifiInfo? = wifiKlasse.getConnectionInfo()
    val datum: Datum = Datum()
    var konditionNamenValide: Boolean = false
    var konditionNetzAngemeldet: Boolean = false
    var konditionRaum: Boolean = false
    private val repositoryDb: RepositoryDb = RepositoryDb(application)
    var result = 0


    private val _nameValide = MutableLiveData<Boolean>().apply {
        konditionNamenValide = value == true
    }
    var nameValide: LiveData<Boolean> = _nameValide

    //Funktion die den eingegebenen Namen in der Datenbank auf Redundanz prüft
    suspend fun namenValidieren(eingabe: String){
         result = repositoryDb.namenPruefen(eingabe)
        _nameValide.postValue( result > 0)
        Log.d("Routine","MessungsId: $result")
    }

    //Pruefung ob der eingegebene Name bereits in der Datenbank vorhanden ist
    fun namenValidierenRoutine(eingabe: String){
        val scope = CoroutineScope(Dispatchers.IO).launch {
           namenValidieren(eingabe)
        }
    }


    private val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = connectionInfo

    }

    var netzwerkInfo: LiveData<WifiInfo> = _netzwerkInfo

    // Speichern der Messung in der Datenbank
    fun messungSpeichern(messung: TblMessung){
        repositoryDb.insertMessung(messung)
    }

    fun netzwerkInfo(): String{
        return connectionInfo!!.ssid
    }

    fun pruefenNetzAnmeldung(): Boolean{
        // Pruefung ob bereits im Neztwerk angemeldet
        if (netzwerkInfo() != "<unknown ssid>") {
            konditionNetzAngemeldet = true
            return true
        }
        konditionNetzAngemeldet = false
        return false
    }

    fun pruefenRaeumlichkeitWahl(auswahl: Int){
        if (auswahl in 0..3){
            konditionRaum = true
            Log.e("Raum: ", auswahl.toString())
        }
    }

    // Freigabe fuer Speicherbutton
    fun buttonFreigabe(): Boolean{
        return konditionNamenValide && konditionNetzAngemeldet && konditionRaum
    }

    val _speicherFreigabe = MutableLiveData<Boolean>().apply {
        value = result > 0 && konditionNetzAngemeldet && konditionRaum
    }

    val speicherFreigabe: LiveData<Boolean> = _speicherFreigabe

    fun getDatum(): String {
        return datum.getDatum()
    }

    fun getZeit(): String {
        return datum.getZeit()
    }

    //Start Coroutine für die Abfrage der Netzwerkinformationen
    fun startUpdateCoroutine() {
        updateJobInit()
        val scope = CoroutineScope(Dispatchers.IO + updateJob).launch {
                startUpdates()
        }
    }

    private lateinit var updateJob: Job
    private lateinit var sinusJob: Job

    //Stoppe Coroutine für die Abfrage der Netzwerkinformationen
    fun stopUpdateCoroutine() {
        if (::updateJob.isInitialized) {
            if (updateJob.isActive || updateJob.isCompleted) {
                updateJob.cancel("job beendet!!")
            }
        } else {
            updateJobInit()
        }
    }


    suspend fun startUpdates() {
        while (updateJob.isActive) {
            try {
                _netzwerkInfo.postValue(wifiKlasse.getConnectionInfo())
                Log.d("Netzwerkinfo","${_netzwerkInfo.value}")


            } catch (e: NullPointerException) {

                println("Update nicht erfolgreich")
            }
            delay(1000)
        }
    }

    fun updateJobInit() {
        updateJob = Job()
        println("Neuer Job initialisiert")

        updateJob.invokeOnCompletion {
            it?.message.let {
                var msg = it
                if (msg.isNullOrBlank()) {
                    msg = "Unbekannter Fehler aufgetreten"
                }
                println("$updateJob ist gecancelt. Grund: $msg")
            }
        }
    }
}