package com.gruppe4.wlan_detektor_pro.model.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung

/**
 * ## Room DAO Interface
 * Das Room Dao Interface beinhaltet sämtliche Funktionen für das Schreiben, Lesen und Updaten der Datenbank
 *
 * @author Bruno Thurnherr
 * @since 1.0.0
 *
 */
@Dao
interface WlanDetektorDao {

    /**
     * Messpunkte in entsprechenden Tabellen einfügen.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTblMesspunkt(tblMesspunkt: TblMesspunkt)

    /**
     * Messpunkte in entsprechenden Tabellen aktualisieren.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    @Update
    suspend fun updateTblMesspunkt(tblMesspunkt: TblMesspunkt)

    /**
     * Messpunkte in entsprechenden Tabellen löschen.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMesspunkt Objekt von TblMesspunkt
     * @see [TblMesspunkt]
     */
    @Delete
    suspend fun deleteTblMesspunkt(tblMesspunkt: TblMesspunkt)

    /**
     * Abfrage von Tabellen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param MesspunktId ID der Messung
     * @see [TblMesspunkt]
     */
    @Query("SELECT * FROM TblMesspunkt Where fkmessungid = :messungsId")
    fun getAllMesspunkte(messungsId: Long): List<TblMesspunkt>

    /**
     * Abfrage von bestimmten Messpunkt
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param MesspunktId ID des Messpunkts
     * @see [TblMesspunkt]
     */
    @Query("SELECT * FROM TblMesspunkt Where messpunktid = :messpunktId")
    fun getMesspunkt(messpunktId: Long): TblMesspunkt

    /**
     * Messung in entsprechenden Tabellen einfügen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMessung Objekt von TblMessung
     * @see [TblMessung]
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTblMessung(tblMessung: TblMessung)

    /**
     * Messung in entsprechenden Tabellen aktualisieren.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMessung Objekt von TblMessung
     * @see [TblMessung]
     */
    @Update
    suspend fun updateTblMessung(tblMessung: TblMessung)

    /**
     * Messung in entsprechenden Tabellen löschen.
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMessung Objekt von TblMessung
     * @see [TblMessung]
     */
    @Delete
    suspend fun deleteTblMessung(tblMessung: TblMessung)

    /**
     * Query für die Ausgabe aller Messungen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param tblMessung Objekt von TblMessung
     * @see [TblMessung]
     */
    @Query("SELECT * FROM TblMessung ORDER BY messungid DESC")
    fun getAllMessung(): List<TblMessung>

    /**
     * Query zur Prüfung ob Name der Messung bereits vorhanden ist
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param name eingegebener Name bei der Messungserfassung
     * @return Messungs id -> Falls Resultat 0 dann ist der Name noch nicht verwendet
     */
    @Query("SELECT messungid FROM TblMessung WHERE name = :name")
    fun nameExists(name: String): Int

    /**
     * Query für die Ausgabe einer bestimmten Messung nach Namen
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param name Messungsname
     * @return Ein Objekt des Typs Messung
     */
    @Query("SELECT * From TblMessung WHERE name = :name")
    fun getDieMessung(name: String): TblMessung

    /**
     * Query für die Ausgabe einer bestimmten Messung nach Id
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param id Messungs Id
     * @return Ein Objekt des Typs Messung
     */
    @Query("SELECT * From TblMessung WHERE messungid = :id")
    fun getDieMessung(id: Long): TblMessung

    /**
     * Query für das Auslesen eines Herstellers des Netzwerk Routers
     * @since 1.0.0
     * @author Bruno Thurnherr
     * @param macadresse Erste sechs Ziffern aus der Empfangenen Macadresse
     * @return Name des Herstellers
     */
    // Abfrage von Tabellen
    @Query("SELECT hersteller FROM TblHersteller where macadresse = :macadresse")
    fun getHersteller(macadresse: String): String
}
