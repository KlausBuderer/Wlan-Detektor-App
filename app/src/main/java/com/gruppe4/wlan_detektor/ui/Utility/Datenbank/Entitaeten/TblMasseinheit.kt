package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMasseinheit
    (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long = 0,
     @NonNull
     @ColumnInfo(name = "sprache") val sprache:String,
     @ColumnInfo(name = "beschreibungStockwerk") val beschreibungStockwerk:String)