package com.gruppe4.wlan_detektor.ui.Utility

import android.app.Application
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor.databinding.HilfeItemBinding
import com.gruppe4.wlan_detektor.ui.Echtzeitmessung.Hilfe
import com.gruppe4.wlan_detektor.ui.Visualisierung.MesspunktVisuAdapter

class HilfeAdapter(private val hilfeListe: List<Hilfe>
                    , private val context: Context
                    ,private val listener: OnItemClickListener)
    :RecyclerView.Adapter<HilfeAdapter.HilfeViewHolder>() {

    inner class HilfeViewHolder(val itemBinding: HilfeItemBinding):
            RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener{
                fun bindItem(hilfe: Hilfe, context: Context){
                   playVideo(hilfe.videoPfade, context, itemBinding.video)
                    itemBinding.tvTitel1.text = hilfe.titel
                    itemBinding.tvBeschreibung1.text = hilfe.beschreibungen
                }

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = bindingAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    private fun playVideo(videoPfade: Uri, context: Context, videoView: VideoView){
        val mediaController = MediaController(context)

            mediaControllerAufruf(videoView,mediaController,videoPfade)

    }

    private fun mediaControllerAufruf(videoView: VideoView, mediaController: MediaController, uri: Uri){
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        mediaController.setAnchorView(videoView)
        videoView.requestFocus()
        videoView.start()
        videoView.setOnCompletionListener {
            videoView.start()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HilfeViewHolder {
        return HilfeViewHolder(HilfeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: HilfeViewHolder, position: Int) {
        val hilfe = hilfeListe[position]
        holder.bindItem(hilfe,context)
    }

    override fun getItemCount(): Int {
        return hilfeListe.size
    }
    interface OnItemClickListener{
        fun onItemClick(position: Int)
    }
}