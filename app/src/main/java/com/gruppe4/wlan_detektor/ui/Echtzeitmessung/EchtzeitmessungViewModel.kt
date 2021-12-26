package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkwahlItem
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.NonCancellable.isActive
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class EchtzeitmessungViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var netzwerkArrayList : ArrayList<NetzwerkwahlItem>

    var wifiManager = getApplication<Application>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    var connectionInfo: WifiInfo = wifiManager.connectionInfo
    var progressFarbeZyklisch: Int = progressBarFarbeEinstellen()


    val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = connectionInfo
    }

    val _progressFarbe = MutableLiveData<Int>().apply {
        value = progressFarbeZyklisch
    }


    val netzwerkInfo: LiveData<WifiInfo> = _netzwerkInfo
    val progressFarbe: LiveData<Int> = _progressFarbe



    fun progressBarFarbeEinstellen(): Int{
        if (wifiManager.connectionInfo.rssi > -60){
            return Color.GREEN
        }else if (wifiManager.connectionInfo.rssi > -70){
            return Color.YELLOW
        }else{
            return Color.RED
        }


    }



    val scope = viewModelScope // could also use an other scope such as viewModelScope if available
    var job: Job? = null

    fun startUpdates() {
        stopUpdates()
        job = scope.launch {
            while(true) {

               connectionInfo = wifiManager.connectionInfo




                progressFarbeZyklisch = progressBarFarbeEinstellen()

                delay(1000)
            }
        }
    }

    fun stopUpdates() {
        job?.cancel()
        job = null
    }
}