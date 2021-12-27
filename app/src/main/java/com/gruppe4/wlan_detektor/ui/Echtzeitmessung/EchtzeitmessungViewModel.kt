package com.gruppe4.wlan_detektor.ui.Echtzeitmessung

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkwahlItem
import kotlinx.coroutines.*
import kotlinx.coroutines.NonCancellable.isActive

class EchtzeitmessungViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var netzwerkArrayList : ArrayList<NetzwerkwahlItem>

    var wifiManager = getApplication<Application>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo
    var progressFarbeZyklisch: Int = progressBarFarbeEinstellen()
    var signalZyklisch: Int = wifiManager.connectionInfo.rssi


    val _netzwerkInfo = MutableLiveData<WifiInfo>().apply {
        value = connectionInfo
    }

    var _signal = MutableLiveData <Int>().apply {
        value = signalZyklisch
    }

    val _progressFarbe = MutableLiveData<Int>().apply {
        value = progressFarbeZyklisch
    }

    fun progressBarFarbeEinstellen(): Int{
        if (wifiManager.connectionInfo.rssi > -60){
            return Color.GREEN
        }else if (wifiManager.connectionInfo.rssi > -70){
            return Color.YELLOW
        }else{
            return Color.RED
        }
    }

    val netzwerkInfo: LiveData<WifiInfo> =  _netzwerkInfo
    val progressFarbe: LiveData<Int> =  _progressFarbe
    var signal: LiveData<Int> = _signal





    suspend fun startUpdates(){
        withContext(Dispatchers.IO){
            while (true) {
                Log.e("Signal: ", signalZyklisch.toString())

                _signal.postValue(signalZyklisch)
                _progressFarbe.postValue(run { progressBarFarbeEinstellen() })



                signalZyklisch = wifiManager.connectionInfo.rssi

                delay(1000)
            }
        }

    }

}