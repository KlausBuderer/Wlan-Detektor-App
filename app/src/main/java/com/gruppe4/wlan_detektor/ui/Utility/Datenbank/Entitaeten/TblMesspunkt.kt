package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMesspunkt
 (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long = 0,
  @NonNull
     @ColumnInfo(name = "gebaeude") val gebaeude:String,
     @ColumnInfo(name = "stockwerkID") val stockwerkID:Int,
     @ColumnInfo(name = "raumname") val raumname:String,
     @ColumnInfo(name = "zusatzinformation") val zusatzinformation:String,
     @ColumnInfo(name = "pegelmessung") val pegelmessung:String,
     @ColumnInfo(name = "masseinheit") val masseinheit:String,
     @ColumnInfo(name = "erfassungsDatum") val erfassungsDatum:String,
     @ColumnInfo(name = "erfassungsZeit") val erfassungsZeit:String,
     @ColumnInfo(name = "aenderungsDatum") val aenderungsDatum:String,
     @ColumnInfo(name = "aenderungsZeit") val aenderungsZeit:String)