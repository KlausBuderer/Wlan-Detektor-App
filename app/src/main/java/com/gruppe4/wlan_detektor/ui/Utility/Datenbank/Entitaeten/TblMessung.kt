package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMessung
    (@PrimaryKey(autoGenerate = true) var id:Long,
     var name:String,
     var ssid:String,
     var raeumlichkeit:Int,
     var erfassungsDatum:String,
     var erfassungsZeit:String)