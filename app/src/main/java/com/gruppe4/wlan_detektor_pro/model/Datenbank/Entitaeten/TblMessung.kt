package com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

/**
 * Messung Tabelle
 * Entität der Datenbank von Messungen </br>
 *
 * @author Bruno Thurnherr
 * @since 1.0.0
 *
 */

@Entity
class TblMessung {

    constructor(name: String, ssid: String, raeumlichkeit: Int, datum: String, zeit: String){
        this.name = name
        this.ssid = ssid
        this.raeumlichkeit = raeumlichkeit
        this.erfassungsDatum = datum
        this.erfassungsZeit = zeit
    }

    constructor(idmessung:Long, name: String, ssid: String, raeumlichkeit: Int, datum: String, zeit: String){
        this.idmessung = idmessung
        this.name = name
        this.ssid = ssid
        this.raeumlichkeit = raeumlichkeit
        this.erfassungsDatum = datum
        this.erfassungsZeit = zeit
    }

    constructor()

 @PrimaryKey(autoGenerate = true)
 @ColumnInfo(name = "messungid")
    var idmessung : Long = 0

    @NotNull
    @ColumnInfo(name = "name")
    lateinit var name:String

    @ColumnInfo(name = "ssid")
    lateinit var  ssid:String

    @ColumnInfo(name = "raeumlichkeit")
     var  raeumlichkeit:Int = 0

    @ColumnInfo(name = "erfassungsDatum")
    lateinit var  erfassungsDatum:String

    @ColumnInfo(name = "erfassungsZeit")
    lateinit var erfassungsZeit:String

}