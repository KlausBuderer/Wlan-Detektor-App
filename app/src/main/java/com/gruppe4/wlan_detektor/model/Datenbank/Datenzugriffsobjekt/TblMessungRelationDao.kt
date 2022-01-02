package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt


import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessungRelation


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
    @Query("SELECT * FROM TblMessungRelation ORDER BY id DESC")
    fun getMessungRelation(): List<TblMessungRelation>?


}