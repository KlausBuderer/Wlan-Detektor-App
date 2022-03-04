package com.gruppe4.wlan_detektor_pro.ui.Visualisierung

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor_pro.databinding.GebauedeItemBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMesspunkt

/**
 * Gebäude Visu Adapter
 * Erstellt Recyclerview in der Visualisierung
 * @author Klaus Buderer
 * @since 1.0.0
 * @see Visualisierung_Grid_Fragment
 */
class GebauedeVisuAdapter(
    private val messpunktListe: List<TblMesspunkt>,
    private val listener: MesspunktVisuAdapter.OnItemClickListener,
    private val application: Application
) : RecyclerView.Adapter<GebauedeVisuAdapter.GebaeudeVisuViewHolder>() {

    inner class GebaeudeVisuViewHolder(val itemBinding: GebauedeItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {

        fun bindItem(
            messpunktListe: List<TblMesspunkt>,
            gebauedenamen: String,
            application: Application
        ) {
            itemBinding.tvTitel.text = gebauedenamen
            itemBinding.rvStockwerk.adapter =
                StockwerkVisuAdapter(messpunktListe, listener, application)
            itemBinding.rvStockwerk.apply {
                layoutManager = LinearLayoutManager(application, RecyclerView.VERTICAL, false)
                setHasFixedSize(true)
            }
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
    ): GebaeudeVisuViewHolder {
        return GebaeudeVisuViewHolder(
            (GebauedeItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ))
        )
    }

    override fun getItemCount(): Int {
        return extrahiereGebaeude(messpunktListe).size
    }

    override fun onBindViewHolder(holder: GebaeudeVisuViewHolder, position: Int) {
        val gebauedenamen = extrahiereGebaeude(messpunktListe)[position]

        holder.bindItem(messpunktProGebaeude(gebauedenamen), gebauedenamen, application)
    }

    /**
     * Filtert alle Gebäude aus einer Liste von Messungen und schreibt sie in eine Liste
     * @author Klaus Buderer
     * @since 1.0.0
     * @param messpunkte Liste von Messpunkten
     * @return Liste mit allen verwendeten Gebäude
     */
     fun extrahiereGebaeude(messpunkte: List<TblMesspunkt>): List<String> {
        val messpunkte = messpunkte
        var gebaeudeSet = mutableSetOf<String>()

        messpunkte.forEach {
            gebaeudeSet.add(it.gebaeude)
        }
        return gebaeudeSet.toList()
    }

    /**
     * Gibt alle Messpunkte mit der selben Gebäudes zurück
     * @author Klaus Buderer
     * @since 1.0.0
     * @param gebauedenamen Gebäudenamen
     * @return Liste mit allen Messpunkten eines Gebäudes
     */
     fun messpunktProGebaeude(gebauedenamen: String): List<TblMesspunkt> {
        val messpunktListeProGebaeude = mutableListOf<TblMesspunkt>()
        messpunktListe.forEach {
            if (it.gebaeude.equals(gebauedenamen)) {
                messpunktListeProGebaeude.add(it)
            }
        }
        return messpunktListeProGebaeude
    }


}

