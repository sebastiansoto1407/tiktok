package com.example.descargadortk

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class HistorialAdapter(
    private val listaVideos: List<VideoGuardado>,
    private val alHacerClic: (VideoGuardado) -> Unit
) : RecyclerView.Adapter<HistorialAdapter.VideoViewHolder>() {

    class VideoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textoTitulo: TextView = view.findViewById(R.id.textoTitulo)
        val textoRuta: TextView = view.findViewById(R.id.textoRuta)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewHolder {
        val vista = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_video, parent, false)
        return VideoViewHolder(vista)
    }

    override fun onBindViewHolder(holder: VideoViewHolder, position: Int) {
        val video = listaVideos[position]
        holder.textoTitulo.text = video.titulo
        holder.textoRuta.text = video.urlLocal

        holder.itemView.setOnClickListener {
            alHacerClic(video)
        }
    }

    override fun getItemCount(): Int = listaVideos.size
}