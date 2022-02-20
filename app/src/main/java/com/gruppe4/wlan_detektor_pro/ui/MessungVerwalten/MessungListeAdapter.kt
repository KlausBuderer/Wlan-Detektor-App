package com.gruppe4.wlan_detektor_pro.ui.MessungVerwalten

import android.app.Application
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor_pro.R
import com.gruppe4.wlan_detektor_pro.databinding.MessungItemBinding
import com.gruppe4.wlan_detektor_pro.model.Datenbank.Entitaeten.TblMessung

class MessungListeAdapter(
    private val messungsListe: List<TblMessung>?,
    private val listener: OnItemClickListener,
    private val application: Application,
    private val kontext: String
) :
    RecyclerView.Adapter<MessungListeAdapter.MessungViewHolder>() {

    inner class MessungViewHolder(var itemBinding: MessungItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        fun bindItem(messung: TblMessung, application: Application) {
            itemBinding.tvMessungNamen.text = messung.name
            itemBinding.tvRaeumlickkeit.text = application.resources.getStringArray(R.array.raeumlichkeiten_array)[messung.raeumlichkeit]
            itemBinding.tvZeitstempel.text = messung.erfassungsDatum + " " + messung.erfassungsZeit

            // Verändere Icon gemäss des Aufrufers
            if (kontext == MESSUNGLISTE_KONTEXT.Loeschen.toString()) {
                itemBinding.messungBarbeiten.setImageResource(R.drawable.ic_baseline_delete_forever_24)
            } else if (kontext == MESSUNGLISTE_KONTEXT.Visualisierung.toString()) {
                itemBinding.messungBarbeiten.setImageResource(R.drawable.ic_visualisierung)
            } else {
                itemBinding.messungBarbeiten.setImageResource(R.drawable.ic_edit)
            }
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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessungViewHolder {
        return MessungViewHolder(MessungItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MessungViewHolder, position: Int) {
        val messung = messungsListe?.get(position)
        if (messung != null) {
            holder.bindItem(messung, application)
        }
    }

    override fun getItemCount(): Int {
        return messungsListe?.size ?: 0
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
