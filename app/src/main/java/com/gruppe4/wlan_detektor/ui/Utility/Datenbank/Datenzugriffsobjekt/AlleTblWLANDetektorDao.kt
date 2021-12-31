package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import androidx.room.OnConflictStrategy.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMasseinheit
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMessung
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMessungRelation
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblRaeumlichkeiten
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblStockwerk
import androidx.room.Transaction as AndroidxRoomTransaction

@Dao
interface AlleTblWLANDetektorDao
{

    // Datensätze in entsprechenden Tabellen einfügen.
    @Insert(onConflict = REPLACE)
    suspend fun inserttblMasseinheit(TblMasseinheit: TblMasseinheit)

    @Insert(onConflict = REPLACE)
    suspend fun insertTblMesspunkt(TblMesspunkt: TblMesspunkt)

    @Insert(onConflict = REPLACE)
    suspend fun insertTblMessung(TblMessung: TblMessung)

    @Insert(onConflict = REPLACE)
    suspend fun insertTblMessungRelation(TblMessungRelation: TblMessungRelation)

    @Insert(onConflict = REPLACE)
    suspend fun insertTblStockwerk(TblStockwerk: TblStockwerk)

    @Insert(onConflict = REPLACE)
    suspend fun insertTblRaeumlichkeiten(TblRaeumlichkeiten: TblRaeumlichkeiten)


    // Datensätze in entsprechenden Tabellen aktualisieren.
    @Update
    suspend fun UpdateTblMasseinheit(TblMasseinheit: TblMasseinheit)

    @Update
    suspend fun UpdateTblMesspunkt(TblMesspunkt: TblMesspunkt)

    @Update
    suspend fun UpdateTblMessung(TblMessung: TblMessung)

    @Update
    suspend fun UpdateTblMessungRelation(TblMessungRelation: TblMessungRelation)

    @Update
    suspend fun UpdateTblStockwerk(TblStockwerk: TblStockwerk)

    @Update
    suspend fun UpdateTblTblRaeumlichkeiten(TblRaeumlichkeiten: TblRaeumlichkeiten)


    // Datensätze in entsprechenden Tabellen löschen.
    @Delete
    suspend fun deleteTblMasseinheit(TblMasseinheit: TblMasseinheit)

    @Delete
    suspend fun deleteTblMesspunkt(TblMesspunkt: TblMesspunkt)

    @Delete
    suspend fun deleteTblMessung(TblMessung: TblMessung)

    @Delete
    suspend fun deleteTblMessungRelation(TblMessungRelation: TblMessungRelation)

    @Delete
    suspend fun deleteTblStockwerk(TblStockwerk: TblStockwerk)

    @Delete
    suspend fun deleteTblTblRaeumlichkeiten(TblRaeumlichkeiten: TblRaeumlichkeiten)

    // Abfrage von Tabellen
    @AndroidxRoomTransaction
    @Query("SELECT * FROM tblmasseinheit ")
    suspend fun gettblmasseinhiet()


}