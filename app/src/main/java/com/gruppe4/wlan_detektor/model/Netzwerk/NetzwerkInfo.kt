package com.gruppe4.wlan_detektor.model.Netzwerk

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkRequest
import android.net.TransportInfo
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi


class NetzwerkInfo(application: Application) {

    var wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo

    val connectivityManager = application.getSystemService(ConnectivityManager::class.java)
    val currentNetwork = connectivityManager.activeNetwork
    val caps = connectivityManager.getNetworkCapabilities(currentNetwork)
    val linkProperties = connectivityManager.getLinkProperties(currentNetwork)

    public fun refreshInfo(): WifiInfo{
        return wifiManager.connectionInfo
    }

    public fun getConnectionInfo(): WifiInfo{
      return  wifiManager.connectionInfo
    }


    fun getConnectionInfo31(): ConnectionInfo{
        return ConnectionInfo(getRssi(), getUpload(), getDownload(), getDomain())
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
    private fun getDownload(): Int{
        return caps?.linkDownstreamBandwidthKbps ?: -1
    }

    private fun getUpload(): Int{
        return caps?.linkUpstreamBandwidthKbps ?: -1
    }

    private fun getRssi(): Int{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            caps?.signalStrength ?: 0
        } else {
            wifiManager.connectionInfo.rssi
        }
    }

    private fun getDomain(): String{
        return if (linkProperties?.domains != null) {
            linkProperties.domains!!
        }else{
            "unknown"
        }
    }

}