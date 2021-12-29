package com.gruppe4.wlan_detektor.ui.Utility

import androidx.lifecycle.ViewModel
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MesspunktItem

class NetzwerklisteViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var netzwerkArrayList = ArrayList<MesspunktItem>()

    val icon = R.drawable.ic_wlan_schwarz
    val ssid = arrayOf(
        "Test1",
        "Test2",
        "Test3"
    )
    val sicherheitsstatus = arrayOf(
        "Verschlüsselt",
        "Nicht verschlüsselt",
        "Verschlüsselt"
    )

    fun getNetwork(): ArrayList<MesspunktItem>{
        netzwerkArrayList = ArrayList()

       /* for (i in ssid.indices){
           val item = MesspunktItem(ssid[i],sicherheitsstatus[i])
            netzwerkArrayList.add(item)
        }*/

        return  netzwerkArrayList

    }
}