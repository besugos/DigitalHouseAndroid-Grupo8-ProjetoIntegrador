package com.besugos.marveluniverse.home.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class CharacterSummaryModel (
    val resourceURI: String?,
    val name: String?,
    val role: String?
): Parcelable

@Parcelize
data class StorySummaryModel (
    val resourceURI: String?,
    val name: String?,
    val type: String?
): Parcelable

@Parcelize
data class EventSummaryModel (
    val resourceURI: String?,
    val name: String?
): Parcelable

@Parcelize
data class ComicSummaryModel (
    val resourceURI: String?,
    val name: String?
): Parcelable