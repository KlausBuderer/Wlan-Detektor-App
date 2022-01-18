package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblHersteller
@Dao
interface WlanDetektorDao {

    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTblMesspunkt(tblMesspunkt: TblMesspunkt)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun updateTblMesspunkt(tblMesspunkt: TblMesspunkt)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblMesspunkt(tblMesspunkt: TblMesspunkt)

    // Abfrage von Tabellen
    @Query("SELECT * FROM TblMesspunkt Where fkmessungid = :messungsId")
    fun getAllMesspunkte(messungsId: Long): List<TblMesspunkt>

    // Abfrage von Tabellen
    @Query("SELECT * FROM TblMesspunkt Where messpunktid = :messpunktId")
    fun getMesspunkt(messpunktId: Long): TblMesspunkt

    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTblMessung(tblMessung: TblMessung)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun updateTblMessung(tblMessung: TblMessung)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblMessung(tblMessung: TblMessung)

    // Abfrage von Tabellen
    @Query("SELECT * FROM TblMessung ORDER BY messungid DESC")
    fun getAllMessung(): List<TblMessung>

    @Query("SELECT messungid FROM TblMessung WHERE name = :name")
    fun nameExists(name: String): Int

    @Query("SELECT * From TblMessung WHERE name = :name")
    fun getDieMessung(name: String): TblMessung

    // Abfrage von Tabellen
    // @Query("SELECT * FROM TblHersteller ORDER BY macadresse DESC")
    // fun getHersteller(): List<TblMessung>
}