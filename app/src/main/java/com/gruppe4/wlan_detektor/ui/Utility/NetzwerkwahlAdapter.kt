package com.gruppe4.wlan_detektor.ui.Utility

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.databinding.NetzwerkwahlItemBinding


class NetzwerkwahlAdapter(private val netzwerkListe: List<ScanResult>
                             , private val listener: OnItemClickListener)
                                : RecyclerView.Adapter<NetzwerkwahlAdapter.NetzViewHolder>() {

    inner class NetzViewHolder(val itemBinding: NetzwerkwahlItemBinding):
        RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener {
            fun bindItem(netz:ScanResult){
                itemBinding.tvNetzwerknamen.text = netz.SSID
                itemBinding.tvSicherheitsstatus.text = netz.BSSID
            }
        init{
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NetzViewHolder {
        return NetzViewHolder(NetzwerkwahlItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: NetzViewHolder, position: Int) {
        val netzwerk = netzwerkListe[position]
        holder.bindItem(netzwerk)
    }

   //Uebergeben der Groesse der Liste fuer die Erstellung des Views
    override fun getItemCount(): Int {
        return netzwerkListe.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}


