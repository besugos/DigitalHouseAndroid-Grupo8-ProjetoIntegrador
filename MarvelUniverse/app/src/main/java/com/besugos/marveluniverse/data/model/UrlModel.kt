package com.besugos.marveluniverse.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class UrlModel (
    val type: String,
    val url: String
): Parcelable