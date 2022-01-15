package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor.model.SinusGenerator
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MesspunktItem
import kotlinx.coroutines.*
import java.lang.NullPointerException

class EchtzeitmessungViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var netzwerkArrayList: ArrayList<MesspunktItem>

    var wifiManager =
        getApplication<Application>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    val connectivityManager =
        getApplication<Application>().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo
    var progressFarbeZyklisch: Int = progressBarFarbeEinstellen()


    val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = connectionInfo
    }

    val _band = MutableLiveData<Double>().apply {
        value = bandUmrechnung()
    }

    val _progressFarbe = MutableLiveData<Int>().apply {
        value = progressFarbeZyklisch
    }

    fun progressBarFarbeEinstellen(): Int {
        if (connectionInfo.rssi > -60) {
            return Color.GREEN
        } else if (connectionInfo.rssi > -70) {
            return Color.YELLOW
        } else {
            return Color.RED
        }
    }

    fun bandUmrechnung(): Double {
        if (connectionInfo.frequency > 5000) {
            return 5.0
        } else if (connectionInfo.frequency in 2000..3000) {
            return 2.4
        }
        return 0.0
    }

    val netzwerkInfo: LiveData<WifiInfo> = _netzwerkInfo
    val progressFarbe: LiveData<Int> = _progressFarbe
    val band: LiveData<Double> = _band
    var tonEin: Boolean = false
    val sinusGenerator = SinusGenerator(application)
    var routine = viewModelScope
    var frequenz: Int = 0

    fun startCoroutine() {
        routine.launch {
            startUpdates()
        }

    }

    fun startSinus() {
        netzwerkInfo.value?.let { sinusGenerator.start(it.rssi) }
    }

    fun stopSinus() {
        sinusGenerator.stopPlaying()
    }

    fun stopCoroutine() {
        routine.cancel()
    }

    suspend fun startUpdates() {
        withContext(Dispatchers.IO) {
            while (isActive) {
                Log.e("couritne: ", "$isActive")
                try {
                    connectionInfo = wifiManager.connectionInfo

                    _netzwerkInfo.postValue(connectionInfo)
                    _progressFarbe.postValue(run { progressBarFarbeEinstellen() })


                } catch (e: NullPointerException) {

                }

                delay(500)
            }

        }

    }


}