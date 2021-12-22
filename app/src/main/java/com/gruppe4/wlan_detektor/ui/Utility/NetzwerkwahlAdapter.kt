package com.gruppe4.wlan_detektor.ui.Utility

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.gruppe4.wlan_detektor.R
import kotlinx.coroutines.Job


class NetzwerkwahlAdapter(val kontext: Context, private val netzwerkListe: ArrayList<NetzwerkwahlItem>) : ArrayAdapter<NetzwerkwahlItem>(kontext, R.layout.netzwerkwahl_item,netzwerkListe) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater: LayoutInflater = LayoutInflater.from(kontext)
        val view : View = inflater.inflate(R.layout.netzwerkwahl_item, null)

        //Verlinkung von Variablen zu dem Listview Item
        val symbol : ImageView = view.findViewById(R.id.wlan_icon)
        val netzwerknamen : TextView = view.findViewById(R.id.tv_netzwerknamen)
        val sicherheitsStatus : TextView = view.findViewById(R.id.tv_sicherheitsstatus)

        //Schreiben der Netzwerkinformationen in die Listview
        symbol.setImageResource(netzwerkListe[position].icon)
        netzwerknamen.text = netzwerkListe[position].ssid
        sicherheitsStatus.text = netzwerkListe[position].verschluesselt

        return view
    }
}
