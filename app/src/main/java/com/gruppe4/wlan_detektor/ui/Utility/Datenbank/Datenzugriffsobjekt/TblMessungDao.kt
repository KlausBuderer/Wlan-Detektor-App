package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMessung

@Dao
interface TblMessungDao {
    @Insert
    suspend fun insert(tblMessung: TblMessung)

    @Delete
    suspend fun delete(tblMessung: TblMessung)

    @Update
    suspend fun update(tblMessung: TblMessung)
}
