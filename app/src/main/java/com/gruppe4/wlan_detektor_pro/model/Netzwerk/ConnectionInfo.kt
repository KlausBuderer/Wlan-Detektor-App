package com.gruppe4.wlan_detektor_pro.model.Netzwerk

/**
 * ## Datenklasse für Netzwerk Informationen für API über 31
 * @since 1.0.0
 * @author Klaus Buderer
 */
data class ConnectionInfo(val rssi:Int,val upLoad:Int, val downLoad:Int,val domain:String)
