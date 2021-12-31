package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblMesspunkt
    (@PrimaryKey(autoGenerate = true) var id:Long,
     var gebaeude:String,
     var stockwerkID:Int,
     var raumname:String,
     var zusatzinformation:String,
     var pegelmessung:String,
     var masseinheit:String,
     var erfassungsDatum:String,
     var erfassungsZeit:String,
     var aenderungsDatum:String,
     var aenderungsZeit:String)