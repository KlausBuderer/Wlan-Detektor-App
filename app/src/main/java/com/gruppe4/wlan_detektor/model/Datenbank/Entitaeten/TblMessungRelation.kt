package com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMessungRelation
    (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long = 0,
     @NonNull

     @ColumnInfo(name = "messungID") val messungID:Int,
     @ColumnInfo(name = "messpunktID") val messpunktID:Int)

