package com.gruppe4.wlan_detektor_pro.model.Netzwerk

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData

sealed class NetworkStatus{

    object Available : NetworkStatus()
    object Unavailabel : NetworkStatus()

}

class NetzwerkHelper(private val context: Context) : LiveData<WifiInfo>() {

    var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback
    val valideNetworkConnections : ArrayList<Network> = ArrayList()
    lateinit var wifiInfo: WifiInfo

    fun getConnectivityManagerCallback() =
        object : ConnectivityManager.NetworkCallback(){
            @RequiresApi(Build.VERSION_CODES.Q)
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                //Instanzierung Callback Klasse
                val networkCapability = connectivityManager.getNetworkCapabilities(network)
                wifiInfo = networkCapability?.transportInfo as WifiInfo


                //Pruefung ob Netzwerk vorhanden
                if (wifiInfo != null){
                    //Netzwerkverbindung verfügbar
                    valideNetworkConnections.add(network)
                    announceStatus()
                }
            }

            fun announceStatus(){
                if (valideNetworkConnections.isNotEmpty()){
                    postValue(wifiInfo)
                } else {
                    postValue(wifiInfo)
                }
            }

            override fun onLost(network: Network) {
                super.onLost(network)
                //Entfernen der Netzwerke aus der Liste und Status updaten
                valideNetworkConnections.remove(network)
                announceStatus()
            }

            override fun onCapabilitiesChanged(
                network: Network,
                networkCapabilities: NetworkCapabilities
            ) {
                super.onCapabilitiesChanged(network, networkCapabilities)
                //Bei Änderung des Status wird geprüft ob Verbindung noch vorhanden ist und Liste und Status wird abgedatet
                if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)){
                    valideNetworkConnections.add(network)
                } else {
                    valideNetworkConnections.remove(network)
                }
                announceStatus()
            }
        }

    override fun onActive() {
        super.onActive()

       connectivityManagerCallback = getConnectivityManagerCallback()
        val networkRequest = NetworkRequest
            .Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest,connectivityManagerCallback)
    }

    override fun onInactive() {
        super.onInactive()
        connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
    }
}