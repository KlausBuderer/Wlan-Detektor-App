package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import android.app.Application
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.StockwerkItemBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt

class StockwerkVisuAdapter(private val messpunktListe: List<TblMesspunkt>
                           , private val listener: MesspunktVisuAdapter.OnItemClickListener
                           , private val application: Application)
    : RecyclerView.Adapter<StockwerkVisuAdapter.StockwerkVisuViewHolder>(){


    inner class StockwerkVisuViewHolder(val itemBinding: StockwerkItemBinding):
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener{

        fun bindItem(messpunktListe: List<TblMesspunkt>, stockwerk: Int, application: Application){
            itemBinding.tvTitel.text = application.resources.getStringArray(R.array.stockwerk_array)[stockwerk]
            itemBinding.rvVisuItem.adapter = MesspunktVisuAdapter(messpunktListe,listener,application)
            itemBinding.rvVisuItem.apply {
                    layoutManager = LinearLayoutManager(application,LinearLayoutManager.HORIZONTAL,false)
                setHasFixedSize(true)
            }
            Log.d("Item aufbau","${itemBinding.tvTitel.text}")
        }

        init {
            itemView.setOnClickListener(this)
        }



        override fun onClick(v: View?) {
            //Do nothing
        }

    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StockwerkVisuViewHolder {
       return StockwerkVisuViewHolder((StockwerkItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)))
    }


    override fun getItemCount(): Int {
        Log.d("Messpunktelistegrösse",extrahiereStockwerke(messpunktListe).size.toString())
       return extrahiereStockwerke(messpunktListe).size

    }

    override fun onBindViewHolder(holder: StockwerkVisuViewHolder, position: Int) {
        val stockwerk = extrahiereStockwerke(messpunktListe)[position]
        Log.d("OnBindViewHolder","${stockwerk}")
       holder.bindItem(messpunktProStockwerk(stockwerk),stockwerk,application)
    }

    private fun extrahiereStockwerke(messpunkte: List<TblMesspunkt>): List<Int> {
        val messpunkte = messpunkte
        var stockwerkSet = mutableSetOf<Int>()

        messpunkte.forEach {
            stockwerkSet.add(it.stockwerkID)

        }

        return stockwerkSet.toList().sortedDescending()
    }

    private fun messpunktProStockwerk(stockwerk: Int):List<TblMesspunkt>{
        val messpunktListeProStockwerk = mutableListOf<TblMesspunkt>()
        messpunktListe.forEach{
            if (it.stockwerkID == stockwerk){
                messpunktListeProStockwerk.add(it)
                }
        }
        return messpunktListeProStockwerk
    }


}

