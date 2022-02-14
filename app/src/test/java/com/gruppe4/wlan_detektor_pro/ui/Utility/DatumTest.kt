package com.gruppe4.wlan_detektor_pro.ui.Utility

import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.junit.Assert.*

@RunWith(JUnit4::class)
class DatumTest {
    val datum = Datum()

    @Test
    fun getDatum() {
        val dat = datum.getDatum()

        assert(dat.isNotEmpty())
    }

    @Test
    fun getZeit() {
        val zeit = datum.getZeit()

        assertTrue(zeit.isNotEmpty())
    }

}