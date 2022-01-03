package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Application
import android.content.Context
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.model.Datenbank.RepositoryDb
import kotlinx.coroutines.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.absoluteValue

class MessungHinzufuegenViewModel(application: Application) : AndroidViewModel(application) {

    var wifiManager = getApplication<Application>().getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var connectionInfo: WifiInfo = wifiManager.connectionInfo
    var konditionNamenValide: Boolean = false
    var konditionNetzAngemeldet: Boolean = false
    var konditionRaum: Boolean = false
    private val repositoryDb: RepositoryDb = RepositoryDb(application)


    //Funktion die den eingegebenen Namen in der Datenbank auf Redundanz prüft
    //TODO Validierung ist noch zu implementieren
    fun namenValidieren(eingabe: String): Boolean? {
        var pruefung = repositoryDb.namenPruefen(eingabe)
        if (pruefung.isCompleted) {
            return pruefung.equals(-1)
        }else{
            return true
        }
    }



    // Speichern der Messung in der Datenbank
    fun messungSpeichern(messung: TblMessung){
        repositoryDb.insertMessung(messung)
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

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    fun getDatum(): String {
        return Calendar.getInstance().time.toString("yyyy/MM/dd")
    }

    fun getZeit(): String {
        return Calendar.getInstance().time.toString("HH:mm:ss")
    }

}