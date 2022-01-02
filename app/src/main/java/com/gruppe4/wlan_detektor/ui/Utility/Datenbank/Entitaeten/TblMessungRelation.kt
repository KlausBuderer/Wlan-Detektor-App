package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMessungRelation
    (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long = 0,
     @NonNull
     @ColumnInfo(name = "name") val messungID:Int,
     @ColumnInfo(name = "name") val messpunktID:Int)

