package com.gruppe4.wlan_detektor_pro.model.Netzwerk

import android.app.Application
import android.graphics.Color
import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito


/**
 * @author Klaus Buderer
 * Testklasse des NetzwerkInfo
 */

@RunWith(JUnit4::class)
class NetzwerkInfoTest(){

val application = Mockito.mock(Application::class.java)

    val netzwerkInfo = NetzwerkInfo(application)

    /**
     * Test der Farbumstellung bei Wertänderung des Pegels
     */
    @Test
     fun progressBarFarbeEinstellenTest(){

        val resultGreen = netzwerkInfo.progressBarFarbeEinstellen(-59)
        val resultYellow = netzwerkInfo.progressBarFarbeEinstellen(-61)
        val resultRed = netzwerkInfo.progressBarFarbeEinstellen(-71)

        assertThat(resultGreen).isEqualTo(Color.GREEN)
        assertThat(resultYellow).isEqualTo(Color.YELLOW)
        assertThat(resultRed).isEqualTo(Color.RED)
    }

}