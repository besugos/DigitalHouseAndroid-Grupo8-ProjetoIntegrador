package com.besugos.marveluniverse.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ImageModel (
    val path: String,
    val extension: String,
):Parcelable {

    fun getThumb(resolution: String? = "detail"): String {
        return "$path/$resolution.$extension".replace("http://", "https://")
    }

}