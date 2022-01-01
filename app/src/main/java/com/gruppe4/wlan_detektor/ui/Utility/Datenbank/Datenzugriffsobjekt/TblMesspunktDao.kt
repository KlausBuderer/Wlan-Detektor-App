package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Update
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMesspunkt

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


}