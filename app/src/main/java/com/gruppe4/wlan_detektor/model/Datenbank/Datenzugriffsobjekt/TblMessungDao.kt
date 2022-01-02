package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt


import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung

@Dao
interface TblMessungDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
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