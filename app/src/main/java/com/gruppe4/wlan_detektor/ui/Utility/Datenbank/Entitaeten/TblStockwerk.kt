package com.gruppe4.wlan_detektor.ui.Utility.Datenbank.Entitaeten

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TblStockwerk
    (@PrimaryKey(autoGenerate = true) var id:Long,
     var sprache:String,
     var beschreibungStockwerk:String)