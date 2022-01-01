package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Update
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMessungRelation


@Dao
interface TblMessungRelationDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
    suspend fun insert(tblMessungRelation: TblMessungRelation)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun update(tblMessungRelation: TblMessungRelation)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun delete(tblMessungRelation: TblMessungRelation)

    // Abfrage von Tabellen

}