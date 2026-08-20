package com.example.descargadortk

import android.app.DownloadManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {

    private lateinit var campoEnlace: EditText
    private lateinit var botonDescargar: Button
    private lateinit var botonHistorial: Button
    private lateinit var vistaVideo: VideoView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        campoEnlace = findViewById(R.id.campoEnlace)
        botonDescargar = findViewById(R.id.botonDescargar)
        botonHistorial = findViewById(R.id.botonHistorial)
        vistaVideo = findViewById(R.id.vistaVideo)

        val mediaController = MediaController(this)
        mediaController.setAnchorView(vistaVideo)
        vistaVideo.setMediaController(mediaController)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.tikwm.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(ApiTikTok::class.java)

        campoEnlace.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                val enlace = s.toString()
                if (enlace.contains("tiktok.com")) {
                    cargarVistaPrevia(api, enlace)
                } else {
                    vistaVideo.visibility = View.GONE
                }
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        botonDescargar.setOnClickListener {
            val enlaceIngresado = campoEnlace.text.toString()
            if (enlaceIngresado.isNotEmpty()) {
                procesarDescarga(api, enlaceIngresado)
            }
        }

        botonHistorial.setOnClickListener {
            val intent = android.content.Intent(this, HistorialActivity::class.java)
            startActivity(intent)
        }
    }

    private fun cargarVistaPrevia(api: ApiTikTok, enlace: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val respuesta = api.obtenerVideoLimpio(enlace)
                if (respuesta.isSuccessful) {
                    val urlLimpia = respuesta.body()?.data?.play
                    if (urlLimpia != null) {
                        withContext(Dispatchers.Main) {
                            vistaVideo.visibility = View.VISIBLE
                            vistaVideo.setVideoURI(Uri.parse(urlLimpia))
                            vistaVideo.requestFocus()
                            vistaVideo.start()
                        }
                    }
                }
            } catch (e: Exception) {
            }
        }
    }

    private fun procesarDescarga(api: ApiTikTok, enlace: String) {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val respuesta = api.obtenerVideoLimpio(enlace)
                if (respuesta.isSuccessful) {
                    val urlLimpia = respuesta.body()?.data?.play
                    val tituloOriginal = respuesta.body()?.data?.title ?: "Video TikTok"
                    val nombreArchivoSeguro = "tk_${System.currentTimeMillis()}"

                    if (urlLimpia != null) {
                        withContext(Dispatchers.Main) {
                            iniciarDescargaNativa(urlLimpia, tituloOriginal, nombreArchivoSeguro)
                        }
                    }
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@MainActivity, "Error: ${e.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun iniciarDescargaNativa(url: String, tituloOriginal: String, nombreArchivoSeguro: String) {
        try {
            val peticion = DownloadManager.Request(Uri.parse(url))
                .setTitle(tituloOriginal)
                .setMimeType("video/mp4")
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "$nombreArchivoSeguro.mp4")

            val gestor = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
            gestor.enqueue(peticion)

            val baseDatos = BaseDatosHistorial.obtenerBaseDatos(this)
            val rutaLocal = "${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)}/$nombreArchivoSeguro.mp4"

            lifecycleScope.launch(Dispatchers.IO) {
                baseDatos.videoDao().guardarVideo(
                    VideoGuardado(titulo = tituloOriginal, urlLocal = rutaLocal)
                )
            }

            Toast.makeText(this, "Descarga iniciada", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Fallo al guardar: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }
}