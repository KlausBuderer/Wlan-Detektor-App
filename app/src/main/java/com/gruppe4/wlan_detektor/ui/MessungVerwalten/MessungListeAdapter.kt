package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.app.Application
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.databinding.MessungItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup
import com.gruppe4.wlan_detektor.R
import com.gruppe4.wlan_detektor.model.Datenbank.Entitaeten.TblMessung

class MessungListeAdapter(private val messungsListe: List<TblMessung>?
                        , private val listener: OnItemClickListener
                        , private val application: Application)
                        :RecyclerView.Adapter<MessungListeAdapter.MessungViewHolder>() {

    inner class MessungViewHolder(var itemBinding: MessungItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener{
        fun bindItem(messung: TblMessung, application: Application){
            itemBinding.tvMessungNamen.text = messung.name
            itemBinding.tvRaeumlickkeit.text = application.resources.getStringArray(R.array.raeumlichkeiten_array)[messung.raeumlichkeit]
            itemBinding.tvZeitstempel.text = messung.erfassungsDatum + " " + messung.erfassungsZeit

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
        return MessungViewHolder(MessungItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))
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

    interface OnItemClickListener{
        fun onItemClick(position: Int)


    }
}