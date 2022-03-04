package com.gruppe4.wlan_detektor_pro.ui.Utility

import java.text.SimpleDateFormat
import java.util.*

/**
 * Helperklasse Datum
 * Liesst aktuelles Datum und die aktuelle Zeit
 * @author Klaus Buderer
 * @since 1.0.0
 */
class Datum {

    fun Date.toString(format: String, locale: Locale = Locale.getDefault()): String {
        val formatter = SimpleDateFormat(format, locale)
        return formatter.format(this)
    }

    /**
     * Gibt aktuelles Datum zurück
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun getDatum(): String {
        return Calendar.getInstance().time.toString("yyyy/MM/dd")
    }

    /**
     * Gibt aktuelle Zeit zurück
     * @author Klaus Buderer
     * @since 1.0.0
     */
    fun getZeit(): String {
        return Calendar.getInstance().time.toString("HH:mm:ss")
    }
}