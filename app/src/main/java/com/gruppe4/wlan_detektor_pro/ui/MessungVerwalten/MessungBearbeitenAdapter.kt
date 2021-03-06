package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.MesspunktItemBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt

/**
 * Messungbearbeitungsadapter
 * Adapter für die Erstellung der Liste von Messpunkten in dem Messungsbearbeitungsbild
 * @author Klaus Buderer
 * @since 1.0.0
 */
class MesspunktBearbeitenAdapter(private val messpunktListe: List<TblMesspunkt>?
                              , private val listener: OnItemClickListener
                              , private val application: Application)
        : RecyclerView.Adapter<MesspunktBearbeitenAdapter.NetzViewHolder>() {

        inner class NetzViewHolder(val itemBinding: MesspunktItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener {

            fun bindItem(messpunkt: TblMesspunkt, application: Application) {
                itemBinding.tvRaumName.text = messpunkt.raumname
                itemBinding.tvGebaeude.text = messpunkt.gebaeude
                itemBinding.tvStockwerk.text = application.resources.getStringArray(R.array.stockwerk_array)[messpunkt.stockwerkID]
                itemBinding.pgProgressBar.progress = messpunkt.pegelmessung
                itemBinding.tvPegel.text = messpunkt.pegelmessung.toString()
            }
            init {
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
            val messpunkt = messpunktListe?.get(position)
            if (messpunkt != null) {
                holder.bindItem(messpunkt, application)
            }
        }

        //Uebergeben der Groesse der Liste fuer die Erstellung des Views
        override fun getItemCount(): Int {
            return messpunktListe?.size ?: 0
        }

    /**
     * Interface um die Elemente der Liste Klickbar zu machen
     * @author Klaus Buderer
     * @since 1.0.0
     */
        interface OnItemClickListener {
            fun onItemClick(position: Int)
        }
    }



