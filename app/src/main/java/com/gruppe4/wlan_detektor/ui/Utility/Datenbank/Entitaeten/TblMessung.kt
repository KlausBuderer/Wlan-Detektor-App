package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMessung
 (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") val id : Long = 0,
  @NonNull
    @ColumnInfo(name = "name") val name:String,
    @ColumnInfo(name = "ssid") val  ssid:String,
    @ColumnInfo(name = "raeumlichkeit") val  raeumlichkeit:Int,
    @ColumnInfo(name = "erfassungsDatum") val  erfassungsDatum:String,
    @ColumnInfo(name = "erfassungsZeit") val  erfassungsZeit:String)