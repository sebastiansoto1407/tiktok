package com.example.descargadortk

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistorialActivity : AppCompatActivity() {

    private lateinit var listaHistorial: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_historial)

        val politicas = StrictMode.VmPolicy.Builder().build()
        StrictMode.setVmPolicy(politicas)

        listaHistorial = findViewById(R.id.listaHistorial)
        listaHistorial.layoutManager = LinearLayoutManager(this)

        cargarHistorial()
    }

    private fun cargarHistorial() {
        lifecycleScope.launch(Dispatchers.IO) {
            val baseDatos = BaseDatosHistorial.obtenerBaseDatos(this@HistorialActivity)
            val videos = baseDatos.videoDao().obtenerHistorial()

            withContext(Dispatchers.Main) {
                listaHistorial.adapter = HistorialAdapter(videos) { videoSeleccionado ->
                    reproducirVideoLocal(videoSeleccionado.urlLocal)
                }
            }
        }
    }

    private fun reproducirVideoLocal(rutaLocal: String) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(Uri.parse("file://$rutaLocal"), "video/mp4")
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(intent)
    }
}