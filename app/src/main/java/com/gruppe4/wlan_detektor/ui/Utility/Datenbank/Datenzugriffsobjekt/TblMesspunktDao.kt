package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMesspunkt

@Dao
interface TblMesspunktDao {
    @Insert
    suspend fun insert(tblMesspunkt: TblMesspunkt)

    @Delete
    suspend fun delete(tblMesspunkt: TblMesspunkt)

    @Update
    suspend fun update(tblMesspunkt: TblMesspunkt)
}
