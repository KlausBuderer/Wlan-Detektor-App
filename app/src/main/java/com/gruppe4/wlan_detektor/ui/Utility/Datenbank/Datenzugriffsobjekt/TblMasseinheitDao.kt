package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMasseinheit

@Dao
interface TblMasseinheitDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
    suspend fun insertTblMasseinheit(tblMasseinheit: TblMasseinheit)

    @Insert(onConflict = REPLACE)
    suspend fun insertInitTblMasseinheit(tblMasseinheit: TblMasseinheit)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun updateTblMasseinheit(tblMasseinheit: TblMasseinheit)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblMasseinheit(tblMasseinheit: TblMasseinheit)

    // Abfrage von Tabellen
    @Query("SELECT * FROM tblmasseinheit ORDER BY id DESC")
    fun getAllMasseinheit(): List<TblMasseinheit>?

}