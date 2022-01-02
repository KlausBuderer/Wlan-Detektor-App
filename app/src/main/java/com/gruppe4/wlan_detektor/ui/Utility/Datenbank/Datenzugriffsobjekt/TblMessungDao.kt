package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt


import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMessung

@Dao
interface TblMessungDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
    suspend fun insertTblMesspunkt(tblMessung: TblMessung)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun updateTblMesspunkt(tblMessung: TblMessung)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblMesspunkt(tblMessung: TblMessung)

    // Abfrage von Tabellen
    @Query("SELECT * FROM TblMessung ORDER BY id DESC")
    fun getAllMesspunkt(): List<TblMessung>?



}