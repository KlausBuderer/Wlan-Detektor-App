package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMasseinheit

@Dao
interface TblMasseinheitDao {
    @Insert
    suspend fun insert(tblMasseinheit: TblMasseinheit)

    @Delete
    suspend fun delete(tblMasseinheit: TblMasseinheit)

    @Update
    suspend fun update(tblMasseinheit: TblMasseinheit)


}

