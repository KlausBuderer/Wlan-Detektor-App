package com.gruppe4.wlan_detektor

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkFragment
import com.gruppe4.wlan_detektor.ui.Utility.NetzwerkwahlItem

class NetzwerklisteViewModel : ViewModel() {
    // TODO: Implement the ViewModel

    var netzwerkArrayList = ArrayList<NetzwerkwahlItem>()

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

    fun getNetwork(): ArrayList<NetzwerkwahlItem>{
        netzwerkArrayList = ArrayList()

        for (i in ssid.indices){
            val item = NetzwerkwahlItem(icon,ssid[i],sicherheitsstatus[i])
            netzwerkArrayList.add(item)
        }

        return  netzwerkArrayList

    }
}