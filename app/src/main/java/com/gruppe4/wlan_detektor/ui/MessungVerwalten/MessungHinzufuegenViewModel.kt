package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Application
import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MessungHinzufuegenViewModel(application: Application) : AndroidViewModel(application) {

    var wifiManager = getApplication<Application>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo
    var konditionNamenValide: Boolean = false
    var konditionNetzAngemeldet: Boolean = false
    var konditionRaum: Boolean = false


    //Funktion die den eingegebenen Namen in der Datenbank auf Redundanz prüft
    fun namenValidieren(eingabe: String): Boolean{
        return eingabe.equals("TestTrue")
    }

    fun netzwerkInfo(): String{
        return connectionInfo.ssid
    }

    fun pruefenNetzAnmeldung(): Boolean{
        // Pruefung ob bereits im Neztwerk angemeldet
        if (netzwerkInfo() != "<unknown ssid>") {
            konditionNetzAngemeldet = true
            return true
        }
        konditionNetzAngemeldet = false
        return false
    }

    fun pruefenRaeumlichkeitWahl(auswahl: Int){
        if (auswahl in 0..3){
            konditionRaum = true
            Log.e("Raum: ", auswahl.toString())
        }
    }

    // Freigabe fuer Speicherbutton
    fun buttonFreigabe(): Boolean{
        return konditionNamenValide && konditionNetzAngemeldet && konditionRaum
    }

    val _speicherFreigabe = MutableLiveData<Boolean>().apply {
        value = konditionNamenValide && konditionNetzAngemeldet && konditionRaum
    }

    val speicherFreigabe: LiveData<Boolean> = _speicherFreigabe

    // Speichern der Messung in der Datenbank
    fun messungSpeichern(){

    }

}