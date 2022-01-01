package com.gruppe4.wlan_detektor.ui.Visualisierung

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.databinding.VisuItemBinding
import com.gruppe4.wlan_detektor.ui.MessungVerwalten.MesspunktItem


class MesspunktVisuAdapter(private val messpunktListe: List<MesspunktItem>
                           , private val listener: OnItemClickListener)
        : RecyclerView.Adapter<MesspunktVisuAdapter.MesspunktVisuViewHolder>() {


        inner class MesspunktVisuViewHolder(val itemBinding: VisuItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener {
            fun bindItem(messpunkt: MesspunktItem){
                itemBinding.tvRaumName.text = messpunkt.raum
                itemBinding.tvGebaeude.text = messpunkt.gebaeude
                itemBinding.tvStockwerk.text = messpunkt.stockwerk
                itemBinding.tvSignal.text = messpunkt.signalstaerke.toString() + " db"
                itemBinding.pgProgressBar.progress = messpunkt.signalstaerke

                if (messpunkt.signalstaerke in -200..-70){
                    itemBinding.pgProgressBar.progressTintList = ColorStateList.valueOf(Color.RED)
                }else if (messpunkt.signalstaerke in -69..-50){
                    itemBinding.pgProgressBar.progressTintList = ColorStateList.valueOf(Color.YELLOW)
                }else if (messpunkt.signalstaerke >= -49){
                    itemBinding.pgProgressBar.progressTintList = ColorStateList.valueOf(Color.GREEN)
                }

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

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MesspunktVisuViewHolder {
            return MesspunktVisuViewHolder(VisuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

        override fun onBindViewHolder(holder: MesspunktVisuViewHolder, position: Int) {
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



