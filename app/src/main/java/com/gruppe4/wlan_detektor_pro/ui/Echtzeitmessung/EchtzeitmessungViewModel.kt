package com.gruppe4.wlan_detektor_pro.ui.Echtzeitmessung

import android.app.Application
import android.graphics.Color
import android.net.wifi.WifiInfo
import android.util.Log
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor_pro.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor_pro.model.Netzwerk.ConnectionInfo
import com.gruppe4.wlan_detektor_pro.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor_pro.model.SinusGenerator
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.IOException
import java.lang.NullPointerException

/**
 * ## Echtzeitmessung Viewmodel
 * @author Klaus Buderer
 * @since 1.0.0
 */
class EchtzeitmessungViewModel(application: Application) : AndroidViewModel(application) {


    private var wifiKlasse: NetzwerkInfo?
    private var wifiInfos: WifiInfo?
    private var repositoryDb: RepositoryDb?
    private var connectionInfoInit: ConnectionInfo?

    init {
        wifiKlasse = NetzwerkInfo(application)
        wifiInfos = wifiKlasse?.getConnectionInfo()
        repositoryDb = RepositoryDb(application)
        connectionInfoInit = wifiKlasse?.getConnectionInfo31()
    }

    private var progressFarbeZyklisch: Int = progressBarFarbeEinstellen()
    private val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = wifiInfos
    }
    val netzwerkInfo: LiveData<WifiInfo> = _netzwerkInfo

    private val _connectionInfo = MutableLiveData<ConnectionInfo>().apply {
       value = wifiKlasse!!.getConnectionInfo31()
    }
    val connectionInfo: LiveData<ConnectionInfo> = _connectionInfo

    private val _band = MutableLiveData<Double>().apply {
        value = bandUmrechnung(wifiInfos!!.frequency)
    }

    private val _hersteller = MutableLiveData<String>().apply {
    }
    var hersteller: LiveData<String> = _hersteller

    private val _progressFarbe = MutableLiveData<Int>().apply {
        value = progressFarbeZyklisch
    }

    /**
     * Sucht Hersteller der Routers aus der Datenbank
     * @return Name des Hersteller des Routers
     * @param macadresse Ersten sechs Ziffern aus der Macadresse
     * @author Klaus Buderer
     * @author Bruno Thurnherr
     * @since 1.0.0
     */
    suspend fun getHerstellerName(macadresse: String):String {
        try {
           return (repositoryDb!!.getHersteller(macadresse))
        } catch (
            e: IOException
        ) {
            Log.e("Hersteller", "Hersteller hat nicht funktioniert )-:")
        }
        return ""
    }
    /**
     * Bearbeitung der Macadresse, damit sie für die Suche des Herstellers in der Datenbank
     * verwendet werden kann.
     * @return Die ersten sechs Ziffern der Macadresse
     * @param macadresse Macadresse des Routers
     * @author Klaus Buderer
     * @author Bruno Thurnherr
     * @since 1.0.0
     */
    suspend fun getFilterMac(macadresse: String): String {
        var macgefilter = macadresse.replace(':', '-')
        macgefilter = macgefilter.dropLast(9)

        return macgefilter.uppercase()

    }

    /**
     * Einstellung der Farbe des Progressbars
     * @author Klaus Buderer
     * @since 1.0.0
     */
    private fun progressBarFarbeEinstellen(): Int {
      return wifiKlasse!!.progressBarFarbeEinstellen(wifiInfos!!.rssi)
    }

    /**
     * Formatierung der Bandweite für die Ausgabe
     * 5GHz oder 2.4GHz
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun bandUmrechnung(band: Int): Double {
        if (band > 5000) {
            return 5.0
        } else if (band in 2000..3000) {
            return 2.4
        }
        return 0.0
    }


    val progressFarbe: LiveData<Int> = _progressFarbe
    val band: LiveData<Double> = _band
    var tonEin: Boolean = false
    private val sinusGenerator = SinusGenerator(application)
    private lateinit var updateJob: Job
    private lateinit var sinusJob: Job
    var frequenz: Int = 0

    /**
     * Start Coroutine für die Abfrage der Netzwerkinformationen
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun startUpdateCoroutine() {
        updateJobInit()
        val scope = CoroutineScope(IO + updateJob).launch {
            startUpdates()
        }
    }

    /**
     * Stoppe Coroutine für die Abfrage der Netzwerkinformationen
     * @author Klaus Buderer
     * @since 1.0.0
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
     * Zyklischer Update der Wifi Daten
     * @author Klaus Buderer
     * @since 1.0.0
     */
    suspend fun startUpdates() {
        while (updateJob.isActive) {
            try {
                _netzwerkInfo.postValue(wifiKlasse?.getConnectionInfo())
                _connectionInfo.postValue(wifiKlasse?.getConnectionInfo31())
                _progressFarbe.postValue(run { wifiKlasse?.progressBarFarbeEinstellen(wifiInfos!!.rssi) })
                _band.postValue(bandUmrechnung(wifiKlasse?.getConnectionInfo()!!.frequency))
                sinusGenerator.frequenz = frequenz
                _hersteller.postValue(getHerstellerName(getFilterMac(wifiKlasse!!.getConnectionInfo()!!.bssid)))
            } catch (e: NullPointerException) {

                println("Update nicht erfolgreich")
            }
            delay(200)
        }
    }

    /**
     * Initialisierung des Coroutine Job des Wifi Daten Update
     * @author Klaus Buderer
     * @since 1.0.0
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

    /**
     * Initialisierung des Coroutine Job des Sinusgenerators
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun sinusJobInit() {
        sinusJob = Job()
        println("Neuer Job initialisiert")

        sinusJob.invokeOnCompletion {
            it?.message.let {
                var msg = it
                if (msg.isNullOrBlank()) {
                    msg = "Unbekannter Fehler aufgetreten"
                }
                println("$sinusJob ist gecancelt. Grund: $msg")
            }
        }
    }

    /**
     * Starten des Sinusgenerators
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun startSinus() {
        sinusJobInit()
        CoroutineScope(IO + sinusJob).launch {
            netzwerkInfo.value?.let {
                sinusGenerator.start() }
        }
    }

    /**
     * Stoppen des Sinusgenerators
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun stopSinus() {
        sinusGenerator.stopPlaying()
        if (::sinusJob.isInitialized) {
            if (sinusJob.isActive || updateJob.isCompleted) {
                sinusJob.cancel("job beendet!!")
            }
        } else {
            sinusJobInit()
        }
    }

    /**
     * Abfrge des Job Zustands des Sinus Generators
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun getSinusJobStatus(): Boolean {
        //Wenn der Job initialisiert wurde wird ein true zurückgegeben
        return ::sinusJob.isInitialized

    }

    /**
     * Abfrge des Job Zustands des Wifi Daten Update
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun getUpdateJobStatus(): Boolean {
        //Wenn der Job initialisiert wurde wird ein true zurückgegeben
        return ::updateJob.isInitialized

    }

}

