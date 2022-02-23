package com.gruppe4.wlan_detektor_pro.model.Netzwerk

import android.app.Application
import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build

/**
 * ## Netzwerk Informationen
 * In dieser Klasse werden Neztwerkinformationen des verbundenen Netzwerks gelesen. </br>
 * Für Versionen grösser API 31 wird [ConnectivityManager] verwendet
 * Für Versionen kleiner API 31 wird [WifiManager] verwendet
 *
 * @author Klaus Buderer
 * @since 1.0.0
 *
 * @property wifiManager Wifimanager objekt
 * @property connectionInfo connectioninfo von Wifimanager
 * @property connectivityManager [ConnectivityManager] Objekt
 * @property currentNetwork Aktuell verbundene Netzwerk
 * @property caps [ConnectivityManager.getNetworkCapabilities]
 * @property linkProperties [ConnectivityManager.getLinkProperties]
 */
class NetzwerkInfo(application: Application) {

    var wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager?
    private var connectionInfo: WifiInfo? = wifiManager?.connectionInfo

    val connectivityManager = application.getSystemService(ConnectivityManager::class.java)
    val currentNetwork = connectivityManager?.activeNetwork
    val caps = connectivityManager?.getNetworkCapabilities(currentNetwork)
    val linkProperties = connectivityManager?.getLinkProperties(currentNetwork)

    /**
     * Aktualisierung der Netzwerkinformationen
     * @since 1.0.0
     * @author Klaus Buderer
     * @return [WifiManager.connectionInfo]
     */
    public fun refreshInfo(): WifiInfo{
        return wifiManager!!.connectionInfo
    }

    /**
     * Getterfunktion der Netzwerkinformationen
     * @since 1.0.0
     * @author Klaus Buderer
     * @return [WifiManager.connectionInfo]
     */
    public fun getConnectionInfo(): WifiInfo?{
      return  wifiManager?.connectionInfo
    }

    /**
     * Getterfunktion der Netzwerkinformationen für API über 31
     * @since 1.0.0
     * @author Klaus Buderer
     * @return [ConnectionInfo]
     */
    fun getConnectionInfo31(): ConnectionInfo{
        return ConnectionInfo(getRssi(), getUpload(), getDownload(), getDomain())
    }

    /**
     * Farbeinstellung des Progressbars gemäss der Signalstärke
     * @since 1.0.0
     * @author Klaus Buderer
     * @return [Color.RED] oder [Color.GREEN] oder [Color.YELLOW]
     */
    fun progressBarFarbeEinstellen(): Int{
        if (connectionInfo?.rssi!! > -60){
            return Color.GREEN
        }else if (connectionInfo?.rssi!! > -70){
            return Color.YELLOW
        }else{
            return Color.RED
        }
    }

    /**
     * Überladene Funktion Farbeinstellung des Progressbars gemäss der Signalstärke
     * @since 1.0.0
     * @author Klaus Buderer
     * @param Aktueller Pegel als Integerwert
     * @return [Color.RED] oder [Color.GREEN] oder [Color.YELLOW]
     */
     fun progressBarFarbeEinstellen(pegel: Int): Int{
        if (pegel > -60){
            return Color.GREEN
        }else if (pegel > -70){
            return Color.YELLOW
        }else{
            return Color.RED
        }
    }

    /**
     * Auslesen der Downstream Bandweite
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Downstream Bandweite
     */
    private fun getDownload(): Int{
        return caps?.linkDownstreamBandwidthKbps ?: -1
    }

    /**
     * Auslesen der Upstream Bandweite
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Downstream Bandweite
     */
    private fun getUpload(): Int{
        return caps?.linkUpstreamBandwidthKbps ?: -1
    }

    /**
     * Auslesen der Singnalstärke des Wlannetzes
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Singnalstärke des Wlannetzes
     */
    private fun getRssi(): Int{
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            caps?.signalStrength ?: 0
        } else {
            wifiManager!!.connectionInfo.rssi
        }
    }

    /**
     * Auslesen der Domaine des Netzwerks
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Domainennamen
     */
    private fun getDomain(): String{
        return if (linkProperties?.domains != null) {
            linkProperties.domains!!
        }else{
            "unknown"
        }
    }

}