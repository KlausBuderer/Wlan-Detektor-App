package com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten;

import androidx.annotation.NonNull
import androidx.room.*

/**
 * Hersteller Tabelle
 * Entität der Datenbank von Router Herstellern
 *
 * @author Bruno Thurnherr
 * @since 1.0.0
 *
 */
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