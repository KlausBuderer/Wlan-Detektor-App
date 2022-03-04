package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import android.app.Application
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import com.google.common.truth.Truth.assertThat

/**
 * @author Klaus Buderer
 * Testklasse des Gebäudeadapters
 */
@RunWith(JUnit4::class)
   class GebauedeVisuAdapterTest: MesspunktVisuAdapter.OnItemClickListener{
    val application = Mockito.mock(Application::class.java)

    val list = listOf<TblMesspunkt>(
        TblMesspunkt(1,1,"Hauptgebäude",1,"Büro1","",-30,"","","",""),
        TblMesspunkt(2,2,"Hauptgebäude",2,"Büro2","",-30,"","","",""),
        TblMesspunkt(3,3,"Nebengebäude",3,"Büro3","",-30,"","","",""),
        TblMesspunkt(4,4,"Nebengebäude",4,"Büro2","",-30,"","","",""),
        )

    val gebauedeVisuAdapter = GebauedeVisuAdapter(list, this,application)
    val expectedResult = listOf<String>("Hauptgebäude","Nebengebäude")

    @Test
    fun extractGebauedeTest(){
        val gebauede = gebauedeVisuAdapter.extrahiereGebaeude(list)
        assertThat(gebauede).isEqualTo(expectedResult)
    }

    @Test
    fun messpunktProGebaeude(){
       val messpunkteProGebauede1 = gebauedeVisuAdapter.messpunktProGebaeude("Hauptgebäude")
        assertThat(messpunkteProGebauede1[0].idmesspunkt).isEqualTo(1)
        assertThat(messpunkteProGebauede1[1].idmesspunkt).isEqualTo(2)
        assertThat(messpunkteProGebauede1.size).isEqualTo(2)

        val messpunkteProGebauede2 = gebauedeVisuAdapter.messpunktProGebaeude("Nebengebäude")
        assertThat(messpunkteProGebauede2[0].idmesspunkt).isEqualTo(3)
        assertThat(messpunkteProGebauede2[1].idmesspunkt).isEqualTo(4)
        assertThat(messpunkteProGebauede2.size).isEqualTo(2)
    }

    override fun onItemClick(messpunktId: Long) {
    }

}