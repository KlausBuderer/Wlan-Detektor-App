package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblMessungRelation

@Dao
interface TblMessungRelationDao
{
    @Insert
    suspend fun insert(tblMessungRelation: TblMessungRelation)

    @Delete
    suspend fun delete(tblMessungRelation: TblMessungRelation)

    @Update
    suspend fun update(tblMessungRelation: TblMessungRelation)
}
