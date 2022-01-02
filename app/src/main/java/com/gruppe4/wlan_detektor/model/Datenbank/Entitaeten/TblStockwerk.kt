package com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblStockwerk
    (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long = 0,
     @NonNull
     @ColumnInfo(name = "sprache") val sprache:String,
     @ColumnInfo(name = "beschreibungStockwerk") val beschreibungStockwerk:String)