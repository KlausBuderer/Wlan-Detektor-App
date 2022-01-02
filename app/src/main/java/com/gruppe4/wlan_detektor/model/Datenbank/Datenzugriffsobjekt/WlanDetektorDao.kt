package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung
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
    @Query("SELECT * FROM TblMesspunkt ORDER BY id DESC")
    fun getAllMesspunkt(): List<TblMesspunkt>?

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
    @Query("SELECT * FROM TblMessung ORDER BY id DESC")
    fun getAllMessung(): List<TblMessung>?

}