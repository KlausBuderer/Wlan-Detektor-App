package com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(foreignKeys = [ForeignKey(entity = TblMessung::class,
   parentColumns = arrayOf("messungid"),
   childColumns = arrayOf("fkmessungid"),
   onDelete = ForeignKey.CASCADE,
   onUpdate = ForeignKey.RESTRICT)]
)
data class TblMesspunkt
 (@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "messpunktid") val idmesspunkt : Long = 0,
  @NonNull
     @ColumnInfo(name = "fkmessungid") val fkmessungid:String,
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