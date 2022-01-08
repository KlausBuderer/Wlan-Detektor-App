package com.gruppe4.wlan_detektor.model.Netzwerk

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager

class NetzwerkInfo(application: Application) {

    var wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo

    public fun refreshInfo(): WifiInfo{
        return wifiManager.connectionInfo
    }

    public fun getConnectionInfo(): WifiInfo{
        return wifiManager.connectionInfo
    }

    fun progressBarFarbeEinstellen(): Int{
        if (connectionInfo.rssi > -60){
            return Color.GREEN
        }else if (connectionInfo.rssi > -70){
            return Color.YELLOW
        }else{
            return Color.RED
        }
    }

    fun progressBarFarbeEinstellen(pegel: Int): Int{
        if (pegel > -60){
            return Color.GREEN
        }else if (pegel > -70){
            return Color.YELLOW
        }else{
            return Color.RED
        }
    }

}