package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.*
import androidx.room.Update
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblStockwerk


@Dao
interface TblStockwerkDao
{
    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
    suspend fun insertTblStockwerk(tblStockwerk: TblStockwerk)

    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun updateTblStockwerk(tblStockwerk: TblStockwerk)

    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblStockwerk(tblStockwerk: TblStockwerk)

    // Abfrage von Tabellen
}