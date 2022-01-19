package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor.model.SinusGenerator
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.io.IOException
import java.lang.NullPointerException

class EchtzeitmessungViewModel(application: Application) : AndroidViewModel(application) {


    private var wifiKlasse: NetzwerkInfo
    private var wifiInfos: WifiInfo
    private var repositoryDb: RepositoryDb

    init {
        wifiKlasse = NetzwerkInfo(application)
        wifiInfos = wifiKlasse.getConnectionInfo()
        repositoryDb = RepositoryDb(application)

    }

    private var progressFarbeZyklisch: Int = progressBarFarbeEinstellen()
    private val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = wifiInfos
    }

    private val _band = MutableLiveData<Double>().apply {
        value = bandUmrechnung(wifiInfos.frequency)
    }

    private val _macadresse = MutableLiveData<String>().apply {
    }
    var macadresse: LiveData<String> = _macadresse

    private val _progressFarbe = MutableLiveData<Int>().apply {
        value = progressFarbeZyklisch
    }

    fun refreshWifiInfo() {
        wifiInfos = wifiKlasse.getConnectionInfo()
    }

    suspend fun getHerstellerName(macadresse: String):String {
        try {
           return (repositoryDb.getHersteller(macadresse))
            Log.e("Hersteller", "Hersteller hat funktioniert")
        } catch (
            e: IOException

        ) {
            Log.e("Hersteller", "Hersteller hat nicht funktioniert )-:")
        }
        return ""
    }

    suspend fun getFilterMac(macadresse: String): String {
        var macgefilter = wifiInfos.bssid.replace(':', '-')
        macgefilter = macgefilter.dropLast(9)

        Log.i("Macadresse",macgefilter.uppercase())
        return macgefilter.uppercase()

    }

    private fun progressBarFarbeEinstellen(): Int {
        if (wifiInfos.rssi > -60) {
            return Color.GREEN
        } else if (wifiInfos.rssi > -80) {
            return Color.YELLOW
        } else {
            return Color.RED
        }
    }

    fun bandUmrechnung(band: Int): Double {
        if (band > 5000) {
            return 5.0
        } else if (band in 2000..3000) {
            return 2.4
        }
        return 0.0
    }

    val netzwerkInfo: LiveData<WifiInfo> = _netzwerkInfo
    val progressFarbe: LiveData<Int> = _progressFarbe
    val band: LiveData<Double> = _band
    var tonEin: Boolean = false
    private val sinusGenerator = SinusGenerator(application)
    private lateinit var updateJob: Job
    private lateinit var sinusJob: Job
    var frequenz: Int = 0

    //Start Coroutine für die Abfrage der Netzwerkinformationen
    fun startUpdateCoroutine() {
        updateJobInit()
        val scope = CoroutineScope(IO + updateJob).launch {
            _macadresse.postValue(getHerstellerName(getFilterMac(wifiKlasse.getConnectionInfo().bssid)))
            startUpdates()

        }
    }

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
                _progressFarbe.postValue(run { wifiKlasse.progressBarFarbeEinstellen(wifiInfos.rssi) })
                _band.postValue(bandUmrechnung(wifiInfos.frequency))

                println(wifiKlasse.getConnectionInfo().rssi)
            } catch (e: NullPointerException) {

                println("Update nicht erfolgreich")
            }
            delay(200)
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

    fun startSinus() {
        sinusJobInit()
        CoroutineScope(IO + sinusJob).launch {
            netzwerkInfo.value?.let { sinusGenerator.start(it.rssi) }
        }
    }

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

    fun getSinusJobStatus(): Boolean {
        //Wenn der Job initialisiert wurde wird ein true zurückgegeben
        return ::sinusJob.isInitialized

    }

    fun getUpdateJobStatus(): Boolean {
        //Wenn der Job initialisiert wurde wird ein true zurückgegeben
        return ::updateJob.isInitialized

    }

}

