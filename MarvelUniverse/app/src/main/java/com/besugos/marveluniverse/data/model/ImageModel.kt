package com.besugos.marveluniverse.data.model

data class ImageModel (
    val path: String,
    val extension: String,
) {

    fun getThumb(resolution: String? = "detail"): String {
        return "$path/$resolution.$extension".replace("http://", "https://")
    }

}