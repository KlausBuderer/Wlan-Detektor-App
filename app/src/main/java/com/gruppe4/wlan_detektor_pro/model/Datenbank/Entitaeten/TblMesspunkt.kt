package com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

/**
 * Messpunkt Tabelle
 * Entität der Datenbank von Messpunkten </br>
 * Fremdschlüsselbeziehung: fkmessungid <-> messungid von TblMessung
 * @see [TblMessung]
 *
 * @author Bruno Thurnherr
 * @since 1.0.0
 *
 */
@Entity(foreignKeys = [ForeignKey(entity = TblMessung::class,
   parentColumns = arrayOf("messungid"),
   childColumns = arrayOf("fkmessungid"),
   onDelete = ForeignKey.CASCADE,
   onUpdate = ForeignKey.RESTRICT)]
)
class TblMesspunkt {

   constructor()

   constructor(
      messpunktId: Long,
      fkmessungid: Long,
      gebaeude: String,
      stockwerkID: Int,
      raumname: String,
      zusatzinformation: String,
      pegelmessung: Int,
      masseinheit: String,
      erfassungsDatum: String,
      erfassungsZeit: String,
      aenderungsDatum: String,
      aenderungsZeit: String,
      bildPfad: String
   ) {
      this.idmesspunkt = messpunktId
      this.fkmessungid = fkmessungid
      this.gebaeude = gebaeude
      this.stockwerkID = stockwerkID
      this.raumname = raumname
      this.zusatzinformation = zusatzinformation
      this.pegelmessung = pegelmessung
      this.masseinheit = masseinheit
      this.erfassungsDatum = erfassungsDatum
      this.erfassungsZeit = erfassungsZeit
      this.aenderungsDatum = aenderungsDatum
      this.aenderungsZeit = aenderungsZeit
      this.bildPfad = bildPfad
   }

   constructor(
      messpunktId: Long,
      fkmessungid: Long,
      gebaeude: String,
      stockwerkID: Int,
      raumname: String,
      zusatzinformation: String,
      pegelmessung: Int,
      masseinheit: String,
      aenderungsDatum: String,
      aenderungsZeit: String,
      bildPfad: String
   ) {
      this.idmesspunkt = messpunktId
      this.fkmessungid = fkmessungid
      this.gebaeude = gebaeude
      this.stockwerkID = stockwerkID
      this.raumname = raumname
      this.zusatzinformation = zusatzinformation
      this.pegelmessung = pegelmessung
      this.masseinheit = masseinheit
      this.aenderungsDatum = aenderungsDatum
      this.aenderungsZeit = aenderungsZeit
      this.bildPfad = bildPfad
   }

   constructor(
      fkmessungid: Long,
      gebaeude: String,
      stockwerkID: Int,
      raumname: String,
      zusatzinformation: String,
      pegelmessung: Int,
      masseinheit: String,
      erfassungsDatum: String,
      erfassungsZeit: String,
      bildPfad: String
   ) {
      this.fkmessungid = fkmessungid
      this.gebaeude = gebaeude
      this.stockwerkID = stockwerkID
      this.raumname = raumname
      this.zusatzinformation = zusatzinformation
      this.pegelmessung = pegelmessung
      this.masseinheit = masseinheit
      this.erfassungsDatum = erfassungsDatum
      this.erfassungsZeit = erfassungsZeit
      this.bildPfad = bildPfad
   }


   @PrimaryKey(autoGenerate = true)
   @ColumnInfo(name = "messpunktid")
   var idmesspunkt: Long = 0

   @NonNull
   @ColumnInfo(name = "fkmessungid")
    var fkmessungid: Long = -1

   @ColumnInfo(name = "gebaeude")
   lateinit var gebaeude: String

   @ColumnInfo(name = "stockwerkID")
   var stockwerkID: Int = -1

   @ColumnInfo(name = "raumname")
   lateinit var raumname: String

   @ColumnInfo(name = "zusatzinformation")
   lateinit var zusatzinformation: String

   @ColumnInfo(name = "pegelmessung")
   var pegelmessung: Int = 0

   @ColumnInfo(name = "masseinheit")
   lateinit var masseinheit: String

   @ColumnInfo(name = "erfassungsDatum")
   lateinit var erfassungsDatum: String

   @ColumnInfo(name = "erfassungsZeit")
   lateinit var erfassungsZeit: String

   @ColumnInfo(name = "aenderungsDatum")
   var aenderungsDatum: String = ""

   @ColumnInfo(name = "aenderungsZeit")
   var aenderungsZeit: String = ""

   @ColumnInfo(name = "bildPfad")
   var bildPfad: String = ""
}