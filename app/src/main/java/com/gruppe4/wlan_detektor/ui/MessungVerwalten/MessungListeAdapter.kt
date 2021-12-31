package com.gruppe4.wlan_detektor.ui.MessungVerwalten

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.databinding.MessungItemBinding
import android.view.LayoutInflater
import android.view.ViewGroup

class MessungListeAdapter(private val messungsListe: List<MessungItem>
                        , private val listener: OnItemClickListener)
                        :RecyclerView.Adapter<MessungListeAdapter.MessungViewHolder>() {

    inner class MessungViewHolder(var itemBinding: MessungItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
            View.OnClickListener{
        fun bindItem(messung: MessungItem){
            itemBinding.tvMessungNamen.text = messung.name
            itemBinding.tvRaeumlickkeit.text = messung.raeumlichkeit
            itemBinding.tvZeitstempel.text = messung.datum + " " + messung.zeit

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
        val messung = messungsListe[position]
        holder.bindItem(messung)
    }

    override fun getItemCount(): Int {
        return messungsListe.size
    }

    interface OnItemClickListener{
        fun onItemClick(position: Int)


    }

}