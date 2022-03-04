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

/**
 * Messung hinzufügen Viewmodel
 * @author Klaus Buderer
 * @since 1.0.0
 */
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

    /**
     * Funktion die den eingegebenen Namen in der Datenbank auf Redundanz prüft
     * @author Klaus Buderer
     * @since 1.0.0
     * @param eingabe Eingegebener Text im Eingabefeld
     */
    suspend fun namenValidieren(eingabe: String) {
        result = repositoryDb.namenPruefen(eingabe)
        _nameValide.postValue(result > 0)
        Log.d("Routine", "MessungsId: $result")
    }

    /**
     * Pruefung ob der eingegebene Name bereits in der Datenbank vorhanden ist
     * @since 1.0.0
     * @param eingabe Eingegebener Text im Eingabefeld
     */
    fun namenValidierenRoutine(eingabe: String) {
        val scope = CoroutineScope(Dispatchers.IO).launch {
            namenValidieren(eingabe)
        }
    }

    private val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = connectionInfo
    }

    var netzwerkInfo: LiveData<WifiInfo> = _netzwerkInfo

    /**
     * Speichern der Messung in der Datenbank
     * @since 1.0.0
     * @param messung Objekt des Typs TblMessung
     */
    fun messungSpeichern(messung: TblMessung) {
        repositoryDb.insertMessung(messung)
    }

    /**
     * Lesen des Namens des verbundenen Wlan Netzwerks
     * @since 1.0.0
     * @return SSid
     */
    fun netzwerkInfo(): String {
        return connectionInfo!!.ssid
    }

    /**
     * Prüft ob eine Verbindung zum Wlan Netzwerk steht
     * @since 1.0.0
     * @return Boolean -> true = verbunden / false = nicht verbunden
     */
    fun pruefenNetzAnmeldung(): Boolean {
        if (netzwerkInfo() != "<unknown ssid>") {
            konditionNetzAngemeldet = true
            return true
        }
        konditionNetzAngemeldet = false
        return false
    }

    /**
     * Prüft ob eine Räumlichkeit ausgewählt wurde
     * @since 1.0.0
     * @param auswahl Index der Auswahl
     */
    fun pruefenRaeumlichkeitWahl(auswahl: Int) {
        if (auswahl in 0..3) {
            konditionRaum = true
            Log.d("Raum: ", auswahl.toString())
        }
    }

    /**
     * Freigabe fuer Speicherbutton
     * @since 1.0.0
     * @param auswahl Index der Auswahl
     */
    fun buttonFreigabe(): Boolean {
        return konditionNamenValide && konditionNetzAngemeldet && konditionRaum
    }

    val _speicherFreigabe = MutableLiveData<Boolean>().apply {
        value = result > 0 && konditionNetzAngemeldet && konditionRaum
    }

    val speicherFreigabe: LiveData<Boolean> = _speicherFreigabe

    /**
     * Lese aktuelles Datum
     * @since 1.0.0
     * @return Datum
     */
    fun getDatum(): String {
        return datum.getDatum()
    }

    /**
     * Lese aktuelle Zeit
     * @since 1.0.0
     * @return Datum
     */
    fun getZeit(): String {
        return datum.getZeit()
    }

    /**
     * Start Coroutine für die Abfrage der Netzwerkinformationen
     * @since 1.0.0
     * @author Klaus Buderer
     */
    fun startUpdateCoroutine() {
        updateJobInit()
        val scope = CoroutineScope(Dispatchers.IO + updateJob).launch {
            startUpdates()
        }
    }

    private lateinit var updateJob: Job
    private lateinit var sinusJob: Job

    /**
     * Stoppe Coroutine für die Abfrage der Netzwerkinformationen
     * @since 1.0.0
     * @author Klaus Buderer
     */
    fun stopUpdateCoroutine() {
        if (::updateJob.isInitialized) {
            if (updateJob.isActive || updateJob.isCompleted) {
                updateJob.cancel("job beendet!!")
            }
        } else {
            updateJobInit()
        }
    }

    /**
     * Starte Aktualisierung Netzwerk Status
     * @since 1.0.0
     * @author Klaus Buderer
     */
    suspend fun startUpdates() {
        while (updateJob.isActive) {
            try {
                _netzwerkInfo.postValue(wifiKlasse.getConnectionInfo())
                Log.d("Netzwerkinfo", "${_netzwerkInfo.value}")
            } catch (e: NullPointerException) {
                println("Update nicht erfolgreich")
            }
            delay(1000)
        }
    }

    /**
     * Initialisiere Coroutine Job -> Aktualisierung Netzwerk Status
     * @since 1.0.0
     * @author Klaus Buderer
     */
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