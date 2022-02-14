package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor_pro.databinding.VisuItemBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt

class MesspunktVisuAdapter(private val messpunktListe: List<TblMesspunkt>
                           , private val listener: OnItemClickListener
                           ,private val application: Application)
        : RecyclerView.Adapter<MesspunktVisuAdapter.MesspunktVisuViewHolder>() {

        inner class MesspunktVisuViewHolder(val itemBinding: VisuItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener {
            fun bindItem(messpunkt: TblMesspunkt, application: Application){
                itemBinding.tvRaumName.text = messpunkt.raumname
                itemBinding.tvZusatzinfo.text = messpunkt.zusatzinformation
                itemBinding.tvSignal.text = messpunkt.pegelmessung.toString() + " db"
                itemBinding.pgProgressBar.progress = messpunkt.pegelmessung

            }
            init{
                itemView.setOnClickListener(this)
            }

            override fun onClick(v: View?) {
                val position: Int = bindingAdapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(messpunktListe[position].idmesspunkt)
                }
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesspunktVisuViewHolder {
            return MesspunktVisuViewHolder(VisuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: MesspunktVisuViewHolder, position: Int) {
            val messpunkt = messpunktListe[position]
            holder.bindItem(messpunkt, application)
        }

        //Uebergeben der Groesse der Liste fuer die Erstellung des Views
        override fun getItemCount(): Int {
            return messpunktListe.size
        }

        interface OnItemClickListener{
            fun onItemClick(messpunktId: Long)
        }
    }



