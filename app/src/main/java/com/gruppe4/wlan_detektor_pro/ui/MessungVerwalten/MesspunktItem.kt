package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

/**
 * ## Messpunktitem Datenklasse
 * @author Klaus Buderer
 * @since 1.0.0
 */
data class MesspunktItem(val id: Int, val raum: String, val gebaeude: String, val stockwerk: String, val signalstaerke: Int, val zeitstempel: String)