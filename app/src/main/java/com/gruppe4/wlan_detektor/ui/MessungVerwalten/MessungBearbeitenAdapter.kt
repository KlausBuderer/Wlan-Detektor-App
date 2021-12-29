package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.databinding.MesspunktItemBinding
import com.gruppe4.wlan_detektor.ui.MesspunktListe
import com.gruppe4.wlan_detektor.ui.MesspunktListe.messpunktListe


class MesspunktBearbeitenAdapter(private val messpunktListe: List<MesspunktItem>
                              , private val listener: OnItemClickListener)
        : RecyclerView.Adapter<MesspunktBearbeitenAdapter.NetzViewHolder>() {


        inner class NetzViewHolder(val itemBinding: MesspunktItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener {
            fun bindItem(messpunkt: MesspunktItem){
                itemBinding.tvNetzwerknamen.text = messpunkt.raum
                itemBinding.tvSicherheitsstatus.text = messpunkt.gebaeude
                itemBinding.tvStockwerk.text = messpunkt.stockwerk

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
            return NetzViewHolder(MesspunktItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: NetzViewHolder, position: Int) {
            val messpunkt = messpunktListe[position]
            holder.bindItem(messpunkt)
        }

        //Uebergeben der Groesse der Liste fuer die Erstellung des Views
        override fun getItemCount(): Int {
            return messpunktListe.size
        }

        interface OnItemClickListener{
            fun onItemClick(position: Int)
        }
    }



