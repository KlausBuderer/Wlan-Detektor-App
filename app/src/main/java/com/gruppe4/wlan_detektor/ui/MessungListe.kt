package com.gruppe4.wlan_detektor.ui

import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MesspunktItem
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MessungItem

object MessungListe {
    val messungListe = listOf<MessungItem>(
        MessungItem(1,"Messung 1","Sunrise2.4GHz", "Wohnung", "31.12.2021","10:00"),
        MessungItem(2,"Messung 2","Sunrise2.4GHz", "Wohnung", "31.12.2021","11:00"),
        MessungItem(3,"Messung 3","Sunrise2.4GHz", "Wohnung", "31.12.2021","12:00"),
        MessungItem(1,"Messung 4","Sunrise2.4GHz", "Wohnung", "31.12.2021","13:00"),
        MessungItem(2,"Messung 5","Sunrise2.4GHz", "Wohnung", "31.12.2021","14:00"),
        MessungItem(3,"Messung 6","Sunrise2.4GHz", "Wohnung", "31.12.2021","15:00")

        )
}