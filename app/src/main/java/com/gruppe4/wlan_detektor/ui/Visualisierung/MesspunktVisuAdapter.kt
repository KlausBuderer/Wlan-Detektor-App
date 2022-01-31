package com.gruppe4.wlan_detektor.ui.Visualisierung

import android.app.Application
import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.databinding.VisuItemBinding
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMesspunkt
import com.gruppe4.wlan_detektor.model.Netzwerk.NetzwerkInfo
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MesspunktItem


class MesspunktVisuAdapter(private val messpunktListe: List<TblMesspunkt>
                           , private val listener: OnItemClickListener
                           ,private val application: Application)
        : RecyclerView.Adapter<MesspunktVisuAdapter.MesspunktVisuViewHolder>() {

    private val netzwerkInfo = NetzwerkInfo(application)

        inner class MesspunktVisuViewHolder(val itemBinding: VisuItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener {
            fun bindItem(messpunkt: TblMesspunkt, application: Application){
                itemBinding.tvRaumName.text = messpunkt.raumname
                itemBinding.tvGebaeude.text = messpunkt.gebaeude
                itemBinding.tvStockwerk.text = application.resources.getStringArray(R.array.stockwerk_array)[messpunkt.stockwerkID]
                itemBinding.tvSignal.text = messpunkt.pegelmessung.toString() + " db"
                itemBinding.pgProgressBar.progress = messpunkt.pegelmessung
                //itemBinding.pgProgressBar.progressTintList = ColorStateList.valueOf(netzwerkInfo.progressBarFarbeEinstellen(messpunkt.pegelmessung))

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



