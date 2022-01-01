package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt


import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Update
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblRaeumlichkeiten


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


}