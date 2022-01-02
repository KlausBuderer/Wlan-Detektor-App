package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt


import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblRaeumlichkeiten


@Dao
interface TblRaeumlichkeitenDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
    suspend fun insertTblRaeumlichkeiten(tblRaeumlichkeiten: TblRaeumlichkeiten)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun updateTblRaeumlichkeiten(tblRaeumlichkeiten: TblRaeumlichkeiten)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblRaeumlichkeiten(tblRaeumlichkeiten: TblRaeumlichkeiten)

    // Abfrage von Tabellen
    @Query("SELECT * FROM TblRaeumlichkeiten ORDER BY id DESC")
    fun getAllRaeumlichkeiten(): List<TblRaeumlichkeiten>?



}