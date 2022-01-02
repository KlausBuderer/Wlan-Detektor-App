package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt


import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt

@Dao
interface TblMesspunktDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
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

}