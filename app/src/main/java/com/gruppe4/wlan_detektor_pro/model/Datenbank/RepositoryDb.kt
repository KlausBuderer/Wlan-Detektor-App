package com.gruppe4.wlan_detektor_pro.model.Datenbank

import android.app.Application
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Datenzugriffsobjekt.WlanDetektorDao
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung
import kotlinx.coroutines.*

/**
 * Room Repository Klasse
 * Datenbankzugriffe werden in dieser Klasse realisiert.
 *
 * @author Bruno Thurnherr
 * @since 1.0.0
 *
 */
class RepositoryDb(application: Application) {

    private val LOG_TAG: String = "RepositoryDb: "

    private val wlanDetektorDao:WlanDetektorDao
    private val coroutineScope = CoroutineScope(Dispatchers.Main)


    init {
    val db = WlanDetektorDb.createInstance(application)
    wlanDetektorDao = db.wlanDetektorDao
    }

    /**
     * Coroutine -> Messung in entsprechenden Tabellen einfügen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param messung Objekt von TblMessung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungHinzufuegenViewModel]
     */
    fun insertMessung(messung: TblMessung){
        coroutineScope.launch (Dispatchers.IO) {
            asyncInsertMessung(messung)
        }
    }

    /**
     * Suspend Function -> Messung in entsprechenden Tabellen einfügen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMessung Objekt von TblMessung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungHinzufuegenViewModel]
     */
    private suspend fun asyncInsertMessung(tblMessung: TblMessung){
        wlanDetektorDao.insertTblMessung(tblMessung)
    }

    /**
     * Coroutine -> Messpunkte in entsprechenden Tabellen einfügen.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    fun insertMesspunkt(messpunkt: TblMesspunkt){
        coroutineScope.launch (Dispatchers.IO) {
            asyncInsertMesspunkt(messpunkt)
        }
    }

    /**
     * Suspend Function -> Messpunkte in entsprechenden Tabellen einfügen.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    private suspend fun asyncInsertMesspunkt(tblMesspunkt: TblMesspunkt){
        wlanDetektorDao.insertTblMesspunkt(tblMesspunkt)
    }

    /**
     * Coroutine -> Messpunkte in entsprechenden Tabellen aktualisieren.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    fun updateMesspunkt(messpunkt: TblMesspunkt){
        coroutineScope.launch(Dispatchers.IO) {
            asyncUpdateMesspunkt(messpunkt)
        }
    }

    /**
     * Suspend Funktion -> Messpunkte in entsprechenden Tabellen aktualisieren.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    private suspend fun asyncUpdateMesspunkt(messpunkt: TblMesspunkt){
        wlanDetektorDao.updateTblMesspunkt(messpunkt)
    }

    /**
     * Coroutine -> Messpunkte in entsprechenden Tabellen aktualisieren.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param messung alter Messungsnamen
     * @param neuerNamen neuer Messungsnamen
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungsnamenAendernViewModel]
     */
    fun updateMessungsNamen(messung: String, neuerNamen: String){
        coroutineScope.launch(Dispatchers.IO){
            asyncUpdateMessung(wlanDetektorDao.getDieMessung(messung), neuerNamen)
        }
    }

    /**
     * Suspend Funktion -> Messpunkte in entsprechenden Tabellen aktualisieren.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param messung Objekt des Typs TblMessung
     * @param neuerNamen neuer Messungsnamen
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungsnamenAendernViewModel]
     */
    private suspend fun asyncUpdateMessung(messung: TblMessung, neuerNamen: String){
        messung.name = neuerNamen
        wlanDetektorDao.updateTblMessung(messung)
    }

    /**
     * Abfrage aller gespeicherten Messungen.
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Liste von allen gespeicherten Messungen
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungListeViewModel]
     */
    suspend fun queryMessung(): List<TblMessung>{
      return  withContext(Dispatchers.IO){
            wlanDetektorDao.getAllMessung()
        }
    }

    /**
     * Abfrage aller gespeicherten Messpunkte einer Messung.
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Liste von allen gespeicherten Messpunkte einer Messung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungBearbeitenViewModel]
     */
    suspend fun getMesspunkte(messungsId: Long): List<TblMesspunkt>{
        return  withContext(Dispatchers.IO){
            wlanDetektorDao.getAllMesspunkte(messungsId)
        }
    }

    /**
     * Abfrage aller gespeicherten Messpunkte einer Messung.
     * @since 1.0.0
     * @author Klaus Buderer
     * @return Liste von allen gespeicherten Messpunkte einer Messung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungBearbeitenViewModel]
     */
    suspend fun getMesspunkt(messpunktId: Long): TblMesspunkt{
        return  withContext(Dispatchers.IO){
            wlanDetektorDao.getMesspunkt(messpunktId)
        }
    }

    /**
     * Query für das Auslesen eines Herstellers des Netzwerk Routers
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param macadresse Erste sechs Ziffern aus der Empfangenen Macadresse
     * @return Name des Herstellers
     */
    suspend fun getHersteller(macadresse: String): String{
        return  withContext(Dispatchers.IO){
            wlanDetektorDao.getHersteller(macadresse)
        }
    }

    /**
     * Query zur Prüfung ob Name der Messung bereits vorhanden ist
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param name eingegebener Name bei der Messungserfassung
     * @return Messungs id -> Falls Resultat 0 dann ist der Name noch nicht verwendet
     */
    suspend fun namenPruefen(namen:String):Int{
      return  wlanDetektorDao?.nameExists(namen)
    }

    /**
     * Query für die Ausgabe einer bestimmten Messung nach Namen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param name Messungsname
     * @return Ein Objekt des Typs Messung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungBearbeitenViewModel]
     */
    suspend fun getMessung(namen: String): TblMessung{
        return coroutineScope.async(Dispatchers.IO) {
            val messung = wlanDetektorDao?.getDieMessung(namen)
            return@async messung
        }.await()
    }

    /**
     * Query für die Ausgabe einer bestimmten Messung nach Id
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param id Messungs Id
     * @return Ein Objekt des Typs Messung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MesspunktErfassungsViewModel]
     */
    suspend fun getMessung(id: Long): TblMessung{
        return coroutineScope.async(Dispatchers.IO) {
            val messung = wlanDetektorDao?.getDieMessung(id)
            return@async messung
        }.await()
    }

    /**
     * Query für die Ausgabe aller Messungen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMessung Objekt von TblMessung
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungListeViewModel]
     */
    suspend fun deleteMessung(messung: TblMessung){
        coroutineScope.launch(Dispatchers.IO){
            wlanDetektorDao.deleteTblMessung(messung)
        }
    }

    /**
     * Messpunkte in entsprechenden Tabellen löschen.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten.MessungBearbeitenViewModel]
     */
    suspend fun deleteMesspunkt(id: Long){
        coroutineScope.launch(Dispatchers.IO){
            wlanDetektorDao.deleteTblMesspunkt(getMesspunkt(id))
        }
    }

}