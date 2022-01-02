package com.gruppe4.wlan_detektor.model.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblStockwerk


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
    @Query("SELECT * FROM TblStockwerk ORDER BY id DESC")
    fun getAllStockwerk(): List<TblStockwerk>?

}