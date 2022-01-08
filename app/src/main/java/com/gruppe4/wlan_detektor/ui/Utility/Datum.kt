package com.gruppe4.wlan_detektor.ui.Utility

import java.text.SimpleDateFormat
import java.util.*

class Datum {

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