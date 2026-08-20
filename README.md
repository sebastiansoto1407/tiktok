# app DescargadorTK - TikTok Video Downloader

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Material Design](https://img.shields.io/badge/Material--Design-757575?style=for-the-badge&logo=material-design&logoColor=white)

DescargadorTK es una aplicación nativa para Android desarrollada en **Kotlin** que permite a los usuarios descargar videos de TikTok directamente a su dispositivo **sin marca de agua** y sin anuncio. La aplicación cuenta con una interfaz moderna, integración de bases de datos locales para el historial y consumo de APIs de terceros.

## Características Principales

* **Descarga Limpia:** Intercepción y extracción de la URL directa del video en formato `.mp4` sin la marca de agua de la plataforma.
* **Vista Previa Integrada:** Reproductor de video nativo (`VideoView`) que se activa automáticamente al detectar un enlace válido en el portapapeles o campo de texto.
* **Historial de Descargas:** Base de datos local que registra cada descarga exitosa, permitiendo al usuario revisar y reproducir videos anteriores directamente desde la app.
* **UI/UX Moderna:** Diseño implementado con **Material Design 3**, con soporte nativo para transiciones automáticas entre **Modo Día y Modo Noche** según la configuración del sistema.

## Tecnologías y Arquitectura

Este proyecto fue construido aplicando principios modernos de desarrollo Android:

* **Lenguaje:** Kotlin
* **Red:** [Retrofit 2](https://square.github.io/retrofit/) y Gson Converter para el consumo asíncrono de la API pública de TikWM.
* **Asincronía:** Kotlin Coroutines (`lifecycleScope`, `Dispatchers.IO`) para manejar peticiones de red y operaciones de base de datos sin bloquear el hilo principal.
* **Base de Datos Local:** [Room Database](https://developer.android.com/training/data-storage/room) como capa de abstracción sobre SQLite para la gestión del historial.
* **Gestor de Descargas:** `DownloadManager` nativo de Android para gestionar la escritura de archivos en el almacenamiento externo.
* **Componentes UI:** `RecyclerView` con adaptadores personalizados, `MaterialCardView`, y componentes de texto tipados.

## Instalación y Uso

1. Clona este repositorio:
   ```bash
   git clone [https://github.com/sebastiansoto1407/tiktok.git](https://github.com/sebastiansoto1407/tiktok.git)
