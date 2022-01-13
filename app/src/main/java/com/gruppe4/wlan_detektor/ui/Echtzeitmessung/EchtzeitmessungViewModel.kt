package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor.model.SinusGenerator
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO
import java.lang.NullPointerException

class EchtzeitmessungViewModel(application: Application) : AndroidViewModel(application) {


    var wifiManager =
        getApplication<Application>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    val connectivityManager =
        getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo
    private var wifiKlasse: NetzwerkInfo
    private var wifiInfos: WifiInfo

    init {
        wifiKlasse = NetzwerkInfo(application)
        wifiInfos = wifiKlasse.getConnectionInfo()

    }

    private var progressFarbeZyklisch: Int = progressBarFarbeEinstellen()
    private val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = wifiInfos
    }

    private val _band = MutableLiveData<Double>().apply {
        value = bandUmrechnung()
    }

    private val _progressFarbe = MutableLiveData<Int>().apply {
        value = progressFarbeZyklisch
    }

    private fun progressBarFarbeEinstellen(): Int {
        if (wifiInfos.rssi > -60) {
            return Color.GREEN
        } else if (wifiInfos.rssi > -70) {
            return Color.YELLOW
        } else {
            return Color.RED
        }
    }

    private fun bandUmrechnung(): Double {
        if (wifiInfos.frequency > 5000) {
            return 5.0
        } else if (wifiInfos.frequency in 2000..3000) {
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
                println(wifiKlasse.getConnectionInfo().rssi)
            } catch (e: NullPointerException) {

                println("Update nicht erfolgreich")
            }
            delay(500)
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
        CoroutineScope(IO + sinusJob).launch{
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

    fun getSinusJobStatus():Boolean{
        //Wenn der Job initialisiert wurde wird ein true zurückgegeben
        return ::sinusJob.isInitialized

    }

    fun getUpdateJobStatus():Boolean{
        //Wenn der Job initialisiert wurde wird ein true zurückgegeben
        return ::updateJob.isInitialized

    }

}

