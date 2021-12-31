package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Datenzugriffsobjekt

import androidx.room.*
import com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten.TblRaeumlichkeiten

@Dao
interface TblRaeumlichkeitenDao
{
    @Insert
    suspend fun insert(tblRaeumlichkeiten: TblRaeumlichkeiten)

    @Delete
    suspend fun delete(tblRaeumlichkeiten: TblRaeumlichkeiten)

    @Update
    suspend fun update(tblRaeumlichkeiten: TblRaeumlichkeiten)
}
