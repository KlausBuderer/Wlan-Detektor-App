package com.gruppe4.wlan_detektor_pro.ui.Utility

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.MediaController
import android.widget.VideoView
import androidx.recyclerview.widget.RecyclerView
import com.gruppe4.wlan_detektor_pro.databinding.HilfeItemBinding
import com.gruppe4.wlan_detektor_pro.ui.Echtzeitmessung.Hilfe

class HilfeAdapter(
    private val hilfeListe: List<Hilfe>,
    private val context: Context,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<HilfeAdapter.HilfeViewHolder>() {

    // Deklarierung des Viewholders der die Verbindung zwischen dem Backend und dem View bewerkstelligt
    inner class HilfeViewHolder(val itemBinding: HilfeItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root),
        View.OnClickListener {
        fun bindItem(hilfe: Hilfe, context: Context) {
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

    // Funktion zum starten des Videos im dafür vorgesehenen Videoview
    private fun playVideo(videoPfade: Uri, context: Context, videoView: VideoView) {
        val mediaController = MediaController(context)

        mediaControllerAufruf(videoView, mediaController, videoPfade)
    }

    // Aufbau des Mediakontrollers für die Wiedergabe des Videos
    private fun mediaControllerAufruf(videoView: VideoView, mediaController: MediaController, uri: Uri) {
        videoView.setMediaController(mediaController)
        videoView.setVideoURI(uri)
        mediaController.setAnchorView(videoView)
        videoView.requestFocus()
        videoView.start()
        videoView.setOnCompletionListener {
            videoView.start()
        }
    }

    // Instanzierung des Viewholders
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HilfeViewHolder {
        return HilfeViewHolder(HilfeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    // Starten des Videos sobald das Item für den User wieder sichtbar ist
    override fun onViewAttachedToWindow(holder: HilfeViewHolder) {
        super.onViewAttachedToWindow(holder)
        playVideo(hilfeListe[holder.bindingAdapterPosition].videoPfade, context, holder.itemBinding.video)
    }

    // Initiale Erstellung des Items und übergabe des Contents
    override fun onBindViewHolder(holder: HilfeViewHolder, position: Int) {
        val hilfe = hilfeListe[position]
        holder.bindItem(hilfe, context)
    }

    override fun getItemCount(): Int {
        return hilfeListe.size
    }
    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }
}
