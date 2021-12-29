package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import java.util.*

data class MesspunktItem(val id: Int, val raum: String, val gebaeude: String, val stockwerk: String, val signalstaerke: Int, val zeitstempel: String)