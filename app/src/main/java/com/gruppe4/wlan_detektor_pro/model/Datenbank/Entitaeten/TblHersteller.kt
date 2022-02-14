package com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten;

import androidx.annotation.NonNull
import androidx.room.*

// CREATE INDEX "indexMacAdresse" ON "TblHersteller" (
// "macadresse"
//);

//@Entity(indices = [Index(value = ["first_name", "last_name"],
//    unique = true)])

@Entity(
    primaryKeys = ["macadresse", "hersteller"]
)
class TblHersteller {

    constructor()

    constructor(
        macadresse: String,
        hersteller: String,

        )

    @NonNull
    @ColumnInfo(name = "macadresse")
    lateinit var macadresse: String

    @ColumnInfo(name = "hersteller")
    lateinit var hersteller: String
}